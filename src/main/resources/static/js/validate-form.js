/*ADD ADDRESS*/

const form = document.getElementById('account-data-form');
const firstName = document.getElementById('address-first-name');
const lastName = document.getElementById('address-last-name');
const phoneNumber = document.getElementById('address-phone-number');
const street = document.getElementById('address-street');
const number = document.getElementById('address-number');
const city = document.getElementById('address-city');
const postalCode = document.getElementById('address-postal-code');
const information = document.getElementById('address-information');

form.addEventListener('submit', e => {
    if(!checkInputs()) {
        e.preventDefault();
    }
});

function checkInputs() {
    const firstNameValue = firstName.value.trim();
    const lastNameValue = lastName.value.trim();
    const phoneNumberValue = phoneNumber.value.trim();
    const streetValue = street.value.trim();
    const numberValue = number.value.trim();
    const cityValue = city.value.trim();
    const postalCodeValue = postalCode.value.trim();
    const informationValue = information.value.trim();

    if (firstNameValue === '') {
        setErrorFor(firstName, 'First name cannot be blank');
    } else {
        setSuccessFor(firstName);
    }

    if (lastNameValue === '') {
        setErrorFor(lastName, 'Last name cannot be blank');
    } else {
        setSuccessFor(lastName);
    }

    if (phoneNumberValue === '') {
        setErrorFor(phoneNumber, 'Phone number cannot be blank')
    } else if (!validatePhoneNumber(phoneNumberValue)) {
        setErrorFor(phoneNumber, 'Wrong format. Example: +48888111000');
    } else {
        setSuccessFor(phoneNumber)
    }

    if (streetValue === '') {
        setErrorFor(street, 'Street cannot be blank')
    } else {
        setSuccessFor(street)
    }

    if (numberValue === '') {
        setErrorFor(number, 'Number street cannot be blank')
    } else {
        setSuccessFor(number)
    }

    if (cityValue === '') {
        setErrorFor(city, 'City cannot be blank')
    } else {
        setSuccessFor(city)
    }

    if (postalCodeValue === '') {
        setErrorFor(postalCode, 'Postal code cannot be blank')
    } else if (!validatePostalCode(postalCodeValue)) {
        setErrorFor(postalCode, 'Wrong format, example: 51-250')
    } else {
        setSuccessFor(postalCode);
    }

    if (informationValue.length > 50) {
        setErrorFor(information, 'The maximum number of characters is 50')
    } else {
        setSuccessFor(information)
    }
}

function setErrorFor(input, message) {
    const formControl = input.parentElement;
    const small = formControl.querySelector('small');
    formControl.className = 'account-data-input error';
    small.innerText = message;
}

function setSuccessFor(input) {
    const formControl = input.parentElement;
    formControl.className = 'account-data-input success';
}

function validatePhoneNumber(phoneNumber) {
    return /\+\d{11}/.test(phoneNumber);
}

function validatePostalCode(postalCode) {
    return /\d{2}-\d{3}/.test(postalCode);
}