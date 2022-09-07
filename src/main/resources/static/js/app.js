document.addEventListener("DOMContentLoaded", function () {

    /**
     * Form Select
     */
    class FormSelect {
        constructor($el) {
            this.$el = $el;
            this.options = [...$el.children];
            this.init();
        }

        init() {
            this.createElements();
            this.addEvents();
            this.$el.parentElement.removeChild(this.$el);
        }

        createElements() {
            // Input for value
            this.valueInput = document.createElement("input");
            this.valueInput.type = "text";
            this.valueInput.name = this.$el.name;

            // Dropdown container
            this.dropdown = document.createElement("div");
            this.dropdown.classList.add("dropdown");

            // List container
            this.ul = document.createElement("ul");

            // All list options
            this.options.forEach((el, i) => {
                const li = document.createElement("li");
                li.dataset.value = el.value;
                li.innerText = el.innerText;

                if (i === 0) {
                    // First clickable option
                    this.current = document.createElement("div");
                    this.current.innerText = el.innerText;
                    this.dropdown.appendChild(this.current);
                    this.valueInput.value = el.value;
                    li.classList.add("selected");
                }

                this.ul.appendChild(li);
            });

            this.dropdown.appendChild(this.ul);
            this.dropdown.appendChild(this.valueInput);
            this.$el.parentElement.appendChild(this.dropdown);
        }

        addEvents() {
            this.dropdown.addEventListener("click", e => {
                const target = e.target;
                this.dropdown.classList.toggle("selecting");

                // Save new value only when clicked on li
                if (target.tagName === "LI") {
                    this.valueInput.value = target.dataset.value;
                    this.current.innerText = target.innerText;
                }
            });
        }
    }

    document.querySelectorAll(".form-group--dropdown select").forEach(el => {
        new FormSelect(el);
    });

    /**
     * Hide elements when clicked on document
     */
    document.addEventListener("click", function (e) {
        const target = e.target;
        const tagName = target.tagName;

        if (target.classList.contains("dropdown")) return false;

        if (tagName === "LI" && target.parentElement.parentElement.classList.contains("dropdown")) {
            return false;
        }

        if (tagName === "DIV" && target.parentElement.classList.contains("dropdown")) {
            return false;
        }

        document.querySelectorAll(".form-group--dropdown .dropdown").forEach(el => {
            el.classList.remove("selecting");
        });
    });

    /**
     * Switching between form steps
     */
    class FormSteps {

        constructor(form) {

            this.$host = 'http://localhost:8080';
            this.$form = form;
            this.$next = form.querySelectorAll(".next-step");
            this.$prev = form.querySelectorAll(".prev-step");
            this.$step = form.querySelector(".form--steps-counter span");
            this.currentStep = 1;
            this.$currentErrorField = form.querySelector(`#step${this.currentStep}error`);

            this.$stepInstructions = form.querySelectorAll(".form--steps-instructions p");
            const $stepForms = form.querySelectorAll("form > div");
            this.slides = [...this.$stepInstructions, ...$stepForms];

            this.$bags = this.$form.querySelector('input[name="quantity"]');
            this.$street = this.$form.querySelector('input[name="street"]');
            this.$city = this.$form.querySelector('input[name="city"]');
            this.$zipCode = this.$form.querySelector('input[name="zipCode"]')
            this.$pickUpTime = this.$form.querySelector('[name="pickUpTime"]')
            this.$pickUpDate = this.$form.querySelector('[name="pickUpDate"]')
            this.$pickUpComment = this.$form.querySelector('[name="pickUpComment"]')
            this.$summary = this.$form.querySelector('#summary');

            this.init();
        }

        /**
         * Init all methods
         */
        init() {
            this.events();
            this.updateForm();
        }

        /**
         * All events that are happening in form
         */
        events() {
            // Next step
            this.$next.forEach(btn => {
                btn.addEventListener("click", e => {
                    e.preventDefault();
                    this.validateStep();
                });
            });

            // Previous step
            this.$prev.forEach(btn => {
                btn.addEventListener("click", e => {
                    e.preventDefault();
                    this.currentStep--;
                    this.updateForm();
                });
            });

            // Form submit
            this.$form.querySelector("form").addEventListener("submit", e => this.submit(e));
        }

        validateStep() {
            if (this.currentStep === 1) {
                let testedValue = 'category'
                let body = {
                    categories : this.$getCheckedCategories(),
                }
                this.testValidation(body, testedValue);
            } else if (this.currentStep === 2) {
                let testedValue = 'quantity';
                let body = {
                    quantity: this.$bags.value,
                }
                this.testValidation(body, testedValue);
            } else if (this.currentStep === 3) {
                let testedValue = 'institution';
                let body = {
                    institution : this.$getCheckedInstitutions(),
                }
                this.testValidation(body, testedValue);
            } else if (this.currentStep === 4) {
                let testedValue = 'address';
                let body = {
                    city : this.$city.value,
                    street : this.$street.value,
                    zipCode : this.$zipCode.value,
                    pickUpDate : this.$pickUpDate.value,
                    pickUpTime : this.$pickUpTime.value,
                }
                this.testValidation(body, testedValue);
            } else {
                this.currentStep++;
                this.updateForm();
            }
        }

        testValidation(body, testedValue){
            const csrfToken = document.cookie.replace(/(?:(?:^|.*;\s*)XSRF-TOKEN\s*\=\s*([^;]*).*$)|^.*$/, '$1');

            fetch(`${this.$host}/donation/${testedValue}`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'X-XSRF-TOKEN': csrfToken,
                },
                body : JSON.stringify(body),
            })
                .then(response => response.json())
                .then(result => {
                    if (!result.success) {
                        this.$currentErrorField.innerText = result.msg;
                    } else {
                        this.$currentErrorField.innerText = '';
                        this.currentStep++;
                        this.updateForm();
                    }
                })
                .catch();
        }

        /**
         * Update form front-end
         * Show next or previous section etc.
         */
        updateForm() {
            this.$step.innerText = this.currentStep;

            this.$currentErrorField = form.querySelector(`#step${this.currentStep}error`);

            this.slides.forEach(slide => {
                slide.classList.remove("active");

                if (slide.dataset.step == this.currentStep) {
                    slide.classList.add("active");
                }
            });

            this.$stepInstructions[0].parentElement.parentElement.hidden = this.currentStep >= 5;
            this.$step.parentElement.hidden = this.currentStep >= 5;

            this.$summary.querySelector('#quantity').innerText = `${this.$bags.value} x worek z: ${this.$getCheckedCategoriesNames()}`;
            this.$summary.querySelector('#institution').innerText = this.$getCheckedInstitutionName();
            this.$summary.querySelector('#street').innerText = this.$street.value;
            this.$summary.querySelector('#city').innerText = this.$city.value;
            this.$summary.querySelector('#zip-code').innerText = this.$zipCode.value;

            this.$summary.querySelector("#pick-up-date").innerText = this.$pickUpDate.value;
            this.$summary.querySelector("#pick-up-time").innerText = this.$pickUpTime.value;
            this.$summary.querySelector("#pick-up-comment").innerText = this.$pickUpComment.value;
        }

        $getCheckedInstitutions() {
            const checkedInstitution = this.$form.querySelector('input[name="institution"]:checked');
            if (checkedInstitution) {
                return checkedInstitution.value;
            } else {
                return 0;
            }
        }

        $getCheckedCategories() {
            const checkedCategories = this.$form.querySelectorAll('input[name="categories"]:checked')
            if (checkedCategories.length === 0) {
                return [];
            }
            return [...checkedCategories].map(category => category.value);
        }

        $getCheckedInstitutionName() {
            const checkedInstitution = this.$form.querySelector('input[name="institution"]:checked');
            if (!checkedInstitution) {
                return 'nie wybrano instytucji';
            }
            return 'dla ' + checkedInstitution.parentElement.querySelector('.title').innerText;
        }

        $getCheckedCategoriesNames() {
            const checkedCategories = this.$form.querySelectorAll('input[name="categories"]:checked')
            if (checkedCategories.length === 0) {
                return 'nie wybrano kategorii';
            }
            return [...checkedCategories]
                .map(category =>
                    category.parentElement.querySelector('.description').innerText)
                .join(', ');
        }


    }

    const form = document.querySelector(".form--steps");
    if (form !== null) {
        new FormSteps(form);
    }
});
