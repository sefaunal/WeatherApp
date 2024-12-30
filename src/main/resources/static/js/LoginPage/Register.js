import {getStorage, ref as sRef, uploadBytesResumable, getDownloadURL} from "https://www.gstatic.com/firebasejs/9.9.0/firebase-storage.js";

let confirmationCode = Math.floor(100000 + Math.random() * 900000);
document.getElementById("validationCodeSpan").innerText = 'Confirmation Code: ' + confirmationCode;

let files = []
let reader = new FileReader();
let temp, ext, fname = "";

let profImg = document.getElementById('registerImage');
let uploadStatus = document.getElementById('registerStatus');
uploadStatus.style.display='none'

let input = document.createElement('input');
input.type = 'file';

input.onchange = e =>{
    files = e.target.files;

    if (files[0].size > 7340032){
        alert("File is Too Big! (Max Size is 7MB)");
        input.value="";
        files = []

    }
    else {
        temp = files[0].name.split('.');
        ext = temp.slice((temp.length-1),(temp.length));
        fname = temp.slice(0,-1).join('.');

        if (ext=="jpg" || ext=="jpeg" || ext=="png"){
            reader.readAsDataURL(files[0]);
        }
        else{
            alert("Only JPG/JPEG & PNG Files Are Allowed!");
            input.value="";
            files = []
        }
    }
}

reader.onload = function(){
    profImg.src = reader.result;
}

document.getElementById('profileImageDIV').onclick = function(){
    input.click();
}

async function ImageUpload(){
    let date = new Date();
    let ImgToUpload = files[0];
    let ImgName = fname + ' - ' + date.toDateString() + ' - ' + date.getHours() + ' ' + date.getMinutes() + ' ' + date.getSeconds() + '.' + ext;

    const storage = getStorage();
    const storageRef = sRef(storage, "Images/"+ImgName);

    const UploadTask = uploadBytesResumable(storageRef, ImgToUpload);

    UploadTask.on('state-changed', (snapshot)=>{
            uploadStatus.style.display=''
            let progress = (snapshot.bytesTransferred / snapshot.totalBytes) * 100;
            $('#registerStatus').val("Upload Status: " + parseInt(progress) + "%");
        },

        (error) =>{
            alert("error: image not uploaded!");
            console.log(error)
        },

        () =>{
            getDownloadURL(UploadTask.snapshot.ref).then((downloadURL)=>{
                createUser(downloadURL)
            });
        }
    );
}

function createUser(imageURL){
    let formData = new FormData();
    formData.append("userName", $("#registerName").val());
    formData.append("userSurname", $("#registerSurname").val());
    formData.append("userPassword", $("#registerPassword").val());
    formData.append('userMail', $("#registerMail").val());
    formData.append("userImageURL", imageURL);

    $.ajax({
        method: 'post',
        processData: false,
        contentType: false,
        cache: false,
        data: formData,
        url: '/register',
        success: function (data){
            if (data === true){
                alert("Your Account Has Been Created Successfully");

                setTimeout(function (){
                    const card = document.querySelector(".card__inner");
                    document.getElementById('frontCard').style.display='';
                    card.classList.remove('is-flipped');

                    uploadStatus.style.display='none'
                    $('#registerStatus').val('');
                    $("#registerName").val('');
                    $("#registerSurname").val('');
                    $("#registerPassword").val('');
                    $("#registerMail").val('');
                    $("#registerPasswordConfirm").val('');

                    $("#validationCode").val('');
                    confirmationCode = Math.floor(100000 + Math.random() * 900000);
                    document.getElementById("validationCodeSpan").innerText = 'Confirmation Code: ' + confirmationCode;

                    input.value="";
                    files = []
                    profImg.src = 'https://st3.depositphotos.com/6672868/13701/v/600/depositphotos_137014128-stock-illustration-user-profile-icon.jpg';

                }, 300)

            }
            else if(data === false){
                alert("Register Failed");
                uploadStatus.style.display='none'
            }
        }
    });
}

$("#signUpForm").submit(function (event) {
    event.preventDefault();

    if (files[0] == null){
        alert("Please Select A Profile Image");
    }
    else {
        checkConfirmation();
    }

});

function checkConfirmation(){
    let validationArea = $("#validationCode").val();
    if (validationArea == confirmationCode){
        checkPassword();
    }
    else {
        alert("Wrong Confirmation Code!");
    }
}

function checkPassword(){
    let password = $("#registerPassword").val();
    let passwordConfirm = $("#registerPasswordConfirm").val();
    if (password == passwordConfirm){
        checkMail();
    }
    else {
        alert("Passwords Does Not Match!");
    }
}

function checkMail(){
    let formData = new FormData();
    formData.append('userMail', $("#registerMail").val());

    $.ajax({
        method: 'post',
        processData: false,
        contentType: false,
        cache: false,
        data: formData,
        url: '/register/checkMail',
        success: function (data){
            if (data === true){
                ImageUpload().then();
            }
            else {
                alert("The E-mail is already is in use, please enter a different mail.");
                $("#registerMail").val('');
            }
        }
    });
}