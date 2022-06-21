let app = Vue.createApp({

    data() {
        return {
            accounts: [],
            clients: {},
            loans: [],
            cards: [],
            color: [],
            type: [],
            password: [],
            ownAccount: [],
            destinyAccount: [],
            amount: [],
            Description: [],
            loanType: [],
            loanPaymentsList: [],
            availableLoans: [],
            loanMaxAmount: [],
            payments: [],
            loanInterestRate: [],
            loanID: [],
            thruDate: [],
        }
    },

    created() {
        axios.get("http://localhost:8080/api/clients/current")
            .then(datos => {
                this.clients = datos.data;
                this.accounts = datos.data.accounts.sort(function (a, b) {
                    return a.id - b.id
                });
                this.loans = datos.data.loans.sort(function (a, b) {
                    return a.id - b.id
                });
                this.cards = datos.data.cards.sort(function (a, b) {
                    return a.id - b.id
                });
                console.log(this.loans)
            });
        axios.get("http://localhost:8080/api/loans")
            .then(datos => {
                this.availableLoans = datos.data;
            })
    },

    computed: {
    },

    methods: {
        logout() {
            axios.post('/api/logout').then(location.reload()).then(() => window.location.replace("http://localhost:8080/web/home.html"))
        },
        createAccount() {
            Swal.fire({
                title: 'Do you want to create a new account?',
                html: `<select id="accountTypeID" v-model="accountType" class="mt-3 form-select">
                <option selected disabled>Account Type</option>
                <option value="Savings">Savings account</option>
                <option value="Current">Current account</option>
              </select>` ,
                showDenyButton: true,
                showCancelButton: true,
                confirmButtonText: 'Save',
                denyButtonText: `Don't save`,
            }).then((result) => {
                if (result.isConfirmed) {

                    let accountType = document.getElementById("accountTypeID").value

                    axios.post('/api/clients/current/accounts', `accountType=${accountType}`, { headers: { 'content-type': 'application/x-www-form-urlencoded' } }).then(location.reload())
                        .catch(error => {
                            Swal.fire({
                                icon: 'error',
                                title: error.response.data,
                                timer: 2000,
                            })
                        })
                }
            })
        },
        createCard() {
            Swal.fire({
                title: 'Do you want to create a new card',
                showDenyButton: true,
                showCancelButton: true,
                confirmButtonText: 'Save',
                denyButtonText: `Don't save`,
            }).then((result) => {
                if (result.isConfirmed) {
                    axios.post('/api/clients/current/cards', `cardColor=${this.color}&cardType=${this.type}&password=${this.password}`, { headers: { 'content-type': 'application/x-www-form-urlencoded' } }).then(
                        Swal.fire('Card created!', '', 'success').then(() => window.location.replace("http://localhost:8080/web/cards.html")))
                        .catch(error => {
                            Swal.fire({
                                icon: 'error',
                                title: error.response.data,
                                timer: 2000,
                            })
                        })
                }
            })
        },
        classToggle(theClass) {
            let card = document.getElementById("card")
            if (card.classList != theClass) {
                card.classList.remove(card.classList)
                card.classList.toggle(theClass)
            }
        },
        sendLoan() {
            Swal.fire({
                title: 'Do you want to apply for the loan?',
                showDenyButton: true,
                showCancelButton: true,
                confirmButtonText: 'Save',
                denyButtonText: `Don't save`,
            }).then((result) => {
                if (result.isConfirmed) {
                    axios.post('/api/loans', `{"id": ${this.loanID},"amount": ${this.amount},"payments": ${this.payments},"destinyAccount": "${this.ownAccount}"}`, { headers: { "Content-Type": "application/json" } })
                        .catch(error => {
                            Swal.fire({
                                icon: 'error',
                                title: error.response.data,
                                timer: 2000,
                            })
                        })
                    Swal.fire('Transfered!', '', 'success').then(() => window.location.replace("http://localhost:8080/web/accounts.html"))

                }
            })
        },
        showAlert() {
            Swal.fire({
                title: 'Do you want to log out?',
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',
                confirmButtonText: 'Yes, log out!'
            }).then((result) => {
                if (result.isConfirmed) {
                    Swal.fire({
                        title: 'Logged out successfully',
                        icon: 'success',
                        timer: 2000,
                    }).then(this.logout)
                }
            }).catch(error => console.warn())
        },
        showAlertFooter() {
            Swal.fire({
                title: 'Terms',
                text: " Lorem ipsum dolor sit amet consectetur adipisicing elit. Atque error eius sunt modi tempore dolor debitis corporis eligendi accusantium culpa! Dolores reiciendis esse illo deserunt vitae ratione expedita ullam magni.",
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',
                cancelButtonText: 'Close',
                confirmButtonText: 'Understood'
            })
        },
        loanAmount() {
            let availableLoan = this.availableLoans
            this.loanMaxAmount = availableLoan.filter(loan => loan.name == this.loanType)[0].maxAmount
            this.loanPaymentsList = availableLoan.filter(loan => loan.name == this.loanType)[0].payments
            this.loanID = availableLoan.filter(loan => loan.name == this.loanType)[0].id
            this.loanInterestRate = availableLoan.filter(loan => loan.name == this.loanType)[0].interestRate
        },
    },

}).mount("#app")