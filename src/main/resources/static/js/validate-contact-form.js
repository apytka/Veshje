/*SEND MESSAGE - CONTACT*/
const formContact = document.getElementById('contact-data-form');
const firstNameContact = document.getElementById('contact-first-name');
const lastNameContact = document.getElementById('contact-last-name');
const phoneNumberContact = document.getElementById('contact-phone-number');
const emailContact = document.getElementById('contact-email');
const messageContact = document.getElementById('contact-message');

formContact.addEventListener('submit', e => {
    if(!checkInputsContact()) {
        e.preventDefault();
    }
});

function checkInputsContact() {
    const firstNameContactValue = firstNameContact.value.trim();
    const lastNameContactValue = lastNameContact.value.trim();
    const phoneNumberContactValue = phoneNumberContact.value.trim();
    const emailContactValue = emailContact.value.trim();
    const messageContactValue = messageContact.value.trim();

    let inputValidatorContact = true;

    if (firstNameContactValue === '') {
        setContactErrorFor(firstNameContact, 'First name cannot be blank');
        inputValidatorContact = false;
    } else {
        setContactSuccessFor(firstNameContact);
    }

    if (lastNameContactValue === '') {
        setContactErrorFor(lastNameContact, 'Last name cannot be blank');
        inputValidatorContact = false;
    } else {
        setContactSuccessFor(lastNameContact);
    }

    if (phoneNumberContactValue === '') {
        setContactErrorFor(phoneNumberContact, 'Phone number cannot be blank');
        inputValidatorContact = false;
    } else if (!validatePhoneNumber(phoneNumberContactValue)) {
        setContactErrorFor(phoneNumberContact, 'Wrong format. Example: +48888111000');
        inputValidatorContact = false;
    } else {
        setContactSuccessFor(phoneNumberContact);
    }

    if (emailContactValue === '') {
        setContactErrorFor(emailContact, 'Email cannot be blank');
        inputValidatorContact = false;
    } else if(!validateEmail(emailContactValue)) {
        setContactErrorFor(emailContact, 'Wrong format');
        inputValidatorContact = false;
    } else {
        setContactSuccessFor(emailContact);
    }

    if (messageContactValue === '') {
        setContactErrorFor(messageContact, 'Message cannot be blank')
        inputValidatorContact = false;
    } else if (messageContactValue.length < 10 || messageContactValue.length > 1500) {
        setContactErrorFor(messageContact, 'Your message must have a minimum of 10 characters and a maximum of 1500 characters')
        inputValidatorContact = false;
    } else {
        setContactSuccessFor(messageContact);
    }

    return inputValidatorContact;
}

function setContactErrorFor(input, message) {
    const formControl = input.parentElement;
    const small = formControl.querySelector('small');
    formControl.className = 'account-data-input error';
    small.innerText = message;
}

function setContactSuccessFor(input) {
    const formControl = input.parentElement;
    formControl.className = 'account-data-input success';
}

function validatePhoneNumber(phoneNumberContact) {
    return /^\+\d{11}$/.test(phoneNumberContact);
}

function validateEmail(emailContact) {
    const re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return re.test(emailContact);
}