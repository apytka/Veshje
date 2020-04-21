// SLIDE BAR
function openSlideMenu() {
    document.getElementById("side-menu").style.width = '300px';
}

function closeSlideMenu() {
    document.getElementById("side-menu").style.width = '0px';

}

// INPUT STYLE
const inputs = document.querySelectorAll(".input");

function focusFunc() {
    let parent = this.parentNode.parentNode;
    parent.classList.add("focus");
}

function blurFunc() {
    let parent = this.parentNode.parentNode;
    if (this.value == "") {
        parent.classList.remove('focus');
    }
}

inputs.forEach(input => {
    input.addEventListener("focus", focusFunc);
    input.addEventListener("blur", blurFunc);
});

// SHOW PASSWORD
var passwordInp = document.getElementById('password-inp');
var passwordEye = document.getElementById('password-eye');
var passwordConfirmInp = document.getElementById('password-confirm-inp');
var passwordConfirmEye = document.getElementById('password-confirm-eye');

passwordEye.addEventListener('click', togglePassword);
passwordConfirmEye.addEventListener('click', togglePasswordConfirm);

function togglePassword() {
    passwordEye.classList.toggle('active');
    passwordInp.type === 'password' ? passwordInp.type = 'text' : passwordInp.type = 'password';
}

function togglePasswordConfirm() {
    passwordConfirmEye.classList.toggle('active');
    passwordConfirmInp.type === 'password' ? passwordConfirmInp.type = 'text' : passwordConfirmInp.type = 'password';

}

// SLIDESHOW
var slideIndex = 1;
showSlides(slideIndex);

function plusSlides(n) {
    showSlides(slideIndex += n)
}

function currentSlide(n) {
    showSlides(slideIndex = n)
}

function showSlides(n) {
    var i;
    var slides = document.getElementsByClassName("slides");
    var dots = document.getElementsByClassName("dot");

    if (n == undefined) {
        n = ++slideIndex
    }
    if (n > slides.length) {
        slideIndex = 1
    }
    if (n < 1) {
        slideIndex = slides.length
    }
    for (i = 0; i < slides.length; i++) {
        slides[i].style.display = "none";
    }
    for (i = 0; i < dots.length; i++) {
        dots[i].className = dots[i].className.replace(" active", "");
    }
    slides[slideIndex - 1].style.display = "block";
    dots[slideIndex - 1].className += " active";
    setTimeout(showSlides, 8000);
}

//ACCORDION MENU
var acc = document.getElementsByClassName("accordion");
var accAccordion;

for (accAccordion = 0; accAccordion < acc.length; accAccordion++) {
    acc[accAccordion].addEventListener("click", function () {
        this.classList.toggle("active");
        var panel = this.nextElementSibling;
        if (panel.style.maxHeight) {
            panel.style.maxHeight = null;
        } else {
            panel.style.maxHeight = panel.scrollHeight + "px";
        }
    });
}

//LOGIN - CREATE ACCOUNT SECTION
function openForm() {
    document.getElementById("form-account-create").style.display = "block";
    document.getElementById("btn-login-create-account").style.display = "none";
    document.getElementById("account-container1").style.display = "none";
    document.getElementById("btn-login-form").style.display = "block";
}

function closeForm() {
    document.getElementById("form-account-create").style.display = "none";
    document.getElementById("btn-login-create-account").style.display = "block";
    document.getElementById("account-container1").style.display = "block";
    document.getElementById("btn-login-form").style.display = "none";
}


//ALERT
function closeAlert() {
    var closeAlert = document.getElementsByClassName("alert");
    closeAlert[0].style.display = "none";
    closeAlert[1].style.display = "none";
}

function closeButtonInformation() {
    var closeAlert = document.getElementsByClassName("alert-information");
    closeAlert[0].style.display = "none";
    closeAlert[1].style.display = "none";
}