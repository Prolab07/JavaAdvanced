(() => {
    "use strict";
    document.addEventListener("click", documentClick);
    function documentClick(e) {
        const targetItem = e.target;
        if (targetItem.closest(".icon-menu")) {
            document.documentElement.classList.toggle("menu-open");
            document.body.classList.toggle("lock-scroll");
        }
    }
    const tabItem = document.querySelectorAll(".tabs__button");
    const tabContent = document.querySelectorAll(".tabs__content-item");
    tabItem.forEach((function(element) {
        element.addEventListener("click", script_open);
    }));
    function script_open(event) {
        const tabTarget = event.currentTarget;
        const button = tabTarget.dataset.button;
        tabItem.forEach((function(item) {
            item.classList.remove("tabs__button-active");
        }));
        tabTarget.classList.add("tabs__button-active");
        tabContent.forEach((function(item) {
            item.classList.remove("tabs__content-item-active");
        }));
        document.querySelector(`#${button}`).classList.add("tabs__content-item-active");
    }
    new Swiper(".swiper", {
        effect: "fade",
        pagination: {
            el: ".swiper-pagination"
        },
        autoplay: {
            delay: 5e3
        }
    });
})();