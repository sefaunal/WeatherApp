import {getStorage, ref as sRef, uploadBytesResumable, getDownloadURL} from "https://www.gstatic.com/firebasejs/9.9.0/firebase-storage.js";

let confirmationCode;
let selected = "None";
let profileImageURL = "";
let profileImageUploadStatus = document.getElementById("imageUpdateStatus");
profileImageUploadStatus.style.display="none";

document.getElementById("accountToggle").onclick = function () {
    confirmationCode = Math.floor(100000 + Math.random() * 900000);
    toggleActive("profile");
}
document.getElementById("passwordToggle").onclick = function () {
    confirmationCode = Math.floor(100000 + Math.random() * 900000);
    toggleActive("password");
}
document.getElementById("popupCancel").onclick = function () {
    toggleActive("popup");
    $("#validationCode").val('');
}

document.getElementById("redirectToUpdate").onclick = function () {
    if (selected == "profile") {
        updateProfile();
    }
    else if (selected == "password") {
        updatePassword();
    }
    else {
        toggleActive();
    }
}

function toggleActive(selectedButton) {
    document.getElementById("validationCodeSpan").innerText = "Confirmation Code: " + confirmationCode;
    selected = selectedButton;

    let blur = document.getElementById("blur");
    blur.classList.toggle("active");

    let popup = document.getElementById("popup");
    popup.classList.toggle("active");
}

function resultPopupToggle() {
    let blur = document.getElementById("blur");
    blur.classList.toggle("active");
    let resultPopup = document.getElementById("resultPopup");
    resultPopup.classList.toggle("active");
}

function updateProfile() {
    if ($("#validationCode").val() == confirmationCode) {
        if (files.length === 1) {
            uploadImageToFirebase().then();
        }
        let formData = new FormData();
        formData.append("userID", $("#userID").val());
        formData.append("userName", $("#userName").val());
        formData.append("userSurname", $("#userSurname").val());
        formData.append("userMail", $("#userMail").val());
        formData.append("userBIO", $("#userBIO").val());
        formData.append("userImage", profileImageURL);

        $.ajax({
            method: 'post',
            processData: false,
            contentType: false,
            cache: false,
            data: formData,
            url: '/profile/updateProfile',
            success: function (data) {

                if(data == "sameMail") {
                    selected = "cancel";
                    $("#redirectToUpdate").click();
                    resultPopupToggle();
                    document.getElementById("resultConfirmButton").onclick = function () {
                        history.go(0);
                    }
                }
                else if (data == "diffMail") {
                    selected = "cancel";
                    $("#redirectToUpdate").click();
                    resultPopupToggle();
                    document.getElementById("resultPopupText").innerText = "Account information has been successfully changed. You will need to login with your new e-mail address";
                    document.getElementById("resultConfirmButton").setAttribute("href", "/logout");
                }
                else{
                    $("#resultPopupText").val(data);
                }
            }
        })
    }
    else {
        alert("Wrong Confirmation Number!");
        confirmationCode = Math.floor(100000 + Math.random() * 900000);
        document.getElementById("validationCodeSpan").innerText = "Confirmation Code: " + confirmationCode;
    }
}

function updatePassword() {
    if ($("#validationCode").val() == confirmationCode) {
        if ($("#new-password").val() == $("#new-password-retype").val()) {
            let formData = new FormData;
            formData.append("userID", $("#userID").val());
            formData.append("oldPassword", $("#old-password").val());
            formData.append("newPassword", $("#new-password").val());

            $.ajax({
                method:'post',
                processData: false,
                contentType: false,
                cache: false,
                data: formData,
                url: 'profile/updatePassword',
                success: function (data) {
                    if (data == "success") {
                        document.getElementById("resultPopupText").innerText = "Password have been successfully changed";
                        selected = "cancel";
                        $("#redirectToUpdate").click();
                        resultPopupToggle();
                        document.getElementById("resultConfirmButton").onclick = function () {
                            history.go(0);
                        }
                    }

                    else if (data == "OldPasswordNotMatch") {
                        document.getElementById("resultPopupText").innerText = "Old password that you entered is wrong";
                        selected = "cancel";
                        $("#redirectToUpdate").click();
                        resultPopupToggle();
                        document.getElementById("resultConfirmButton").onclick = function () {
                            history.go(0);
                        }
                    }

                    else {
                        document.getElementById("resultPopupText").innerText = data;
                        selected = "cancel";
                        $("#redirectToUpdate").click();
                        resultPopupToggle();
                        document.getElementById("resultConfirmButton").onclick = function () {
                            history.go(0);
                        }
                    }
                }
            })
        }
        else {
            alert("New Passwords Do Not Match!");
        }
    }
    else {
        alert("Wrong Confirmation Code!");
        confirmationCode = Math.floor(100000 + Math.random() * 900000);
        document.getElementById("validationCodeSpan").innerText = "Confirmation Code: " + confirmationCode;
    }
}

async function uploadImageToFirebase(){
    let date = new Date();
    let ImgToUpload = files[0];
    let ImgName = fname + ' - ' + date.toDateString() + ' - ' + date.getHours() + ' ' + date.getMinutes() + ' ' + date.getSeconds() + '.' + ext;

    const storage = getStorage();
    const storageRef = sRef(storage, "Images/"+ImgName);

    const UploadTask = uploadBytesResumable(storageRef, ImgToUpload);

    UploadTask.on('state-changed', (snapshot)=>{
            profileImageUploadStatus.style.display=''
            let progress = (snapshot.bytesTransferred / snapshot.totalBytes) * 100;
            $('#imageUpdateStatus').val("Upload Status: " + parseInt(progress) + "%");
        },

        (error) =>{
            alert("error: image not uploaded!");
            console.log(error)
        },

        () =>{
            getDownloadURL(UploadTask.snapshot.ref).then((downloadURL)=>{
                profileImageURL = downloadURL;
            });
        }
    );
}
