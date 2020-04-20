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