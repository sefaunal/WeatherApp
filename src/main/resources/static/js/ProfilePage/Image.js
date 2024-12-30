let files = []
let reader = new FileReader();

let profImg = document.getElementById('ProfileImage');

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

document.getElementById('ProfileImage').onclick = function(){
    input.click();
}