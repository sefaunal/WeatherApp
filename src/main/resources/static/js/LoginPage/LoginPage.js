const card = document.querySelector(".card__inner");
const btn = document.getElementById("FlipBtnFront");
const btn2 = document.getElementById("FlipBtnBack");

btn.addEventListener("click", function (e) {
  card.classList.toggle('is-flipped');
  setTimeout(function(){
    document.getElementById('frontCard').style.display='none';
    console.log("Executed after 1 second");
}, 1000);
  
});

btn2.addEventListener("click", function (e) {
  document.getElementById('frontCard').style.display='';
  card.classList.remove('is-flipped');
});
