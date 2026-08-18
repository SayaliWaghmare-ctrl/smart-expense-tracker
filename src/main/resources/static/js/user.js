/**
 * 
 */

 document.addEventListener("DOMContentLoaded", function () {

    const form = document.getElementById("userForm");

    const password =
        document.getElementById("password");

    const togglePassword =
        document.getElementById("togglePassword");

    const passwordMessage =
        document.getElementById("passwordMessage");

    const submitButton =
        document.getElementById("submitButton");

    const buttonText =
        document.getElementById("buttonText");

    const loadingText =
        document.getElementById("loadingText");


    /*
     * Show / Hide Password
     */

    togglePassword.addEventListener("click", function () {

        if (password.type === "password") {

            password.type = "text";

            togglePassword.textContent = "Hide";

        } else {

            password.type = "password";

            togglePassword.textContent = "Show";
        }

    });


    /*
     * Password Strength
     */

    password.addEventListener("input", function () {

        const value = password.value;

        if (value.length === 0) {

            passwordMessage.textContent = "";

        } else if (value.length < 6) {

            passwordMessage.textContent =
                "Password should contain at least 6 characters.";

            passwordMessage.style.color = "#dc2626";

        } else if (value.length < 10) {

            passwordMessage.textContent =
                "Password strength: Medium";

            passwordMessage.style.color = "#d97706";

        } else {

            passwordMessage.textContent =
                "Password strength: Strong";

            passwordMessage.style.color = "#16a34a";
        }

    });


    /*
     * Form Submit
     */

    form.addEventListener("submit", function () {

        submitButton.disabled = true;

        buttonText.style.display = "none";

        loadingText.style.display = "inline";

    });


    /*
     * Reset Form
     */

    document
        .getElementById("resetButton")
        .addEventListener("click", function () {

            password.type = "password";

            togglePassword.textContent = "Show";

            passwordMessage.textContent = "";

        });


    /*
     * Automatically hide success message
     */

    const successMessage =
        document.querySelector(".success-alert");

    if (successMessage) {

        setTimeout(function () {

            successMessage.style.opacity = "0";

            successMessage.style.transition =
                "opacity 0.5s ease";

            setTimeout(function () {

                successMessage.remove();

            }, 500);

        }, 4000);
    }

});