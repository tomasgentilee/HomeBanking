let app = Vue.createApp({

    data() {
        return {
            accounts: [],
            clients: {},
            ownAccount: [],
            destinyAccount: [],
            amount: [],
            Description: [],
            accountBalance: [],
            cardAmount: [],
            cardNumber: "",
            cardHolder: "",
            thruDate: "",
            description: "",
            cvv: [],

        }
    },

    created() {
        axios.get("/api/clients/current")
            .then(datos => {
                this.clients = datos.data;
                this.accounts = datos.data.accounts;
            })
    },

    computed: {
        nombreEntero() {
        },
    },

    methods: {
        logout() {
            axios.post('/api/logout').then(location.reload()).then(() => window.location.replace("/web/home.html"))
        },
        transfer() {
            axios.post('/api/transactions', `ownAccountnumber=${this.ownAccount}&description=${this.Description}&amount=${this.amount}&numberAccountDestinatario=${this.destinyAccount}`, { headers: { 'content-type': 'application/x-www-form-urlencoded' } })

        },
        balance() {
            this.accountBalance = this.accounts.filter(account => account.number == this.ownAccount)[0].balance
        },
        showAlertTransfer() {
            Swal.fire({
                title: 'Do you want to do the transaction?',
                showDenyButton: true,
                showCancelButton: true,
                confirmButtonText: 'Save',
                denyButtonText: `Don't save`,
            }).then((result) => {
                if (result.isConfirmed) {
                axios.post('/api/transactions', `ownAccountnumber=${this.ownAccount}&description=${this.Description}&amount=${this.amount}&numberAccountDestinatario=${this.destinyAccount}`, { headers: { 'content-type': 'application/x-www-form-urlencoded' }})  
                    .then(()=>
                        Swal.fire('Transfered!', '', 'success').then(() => window.location.replace("/web/accounts.html")))
                        
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
        cardTransactionCredit() {
            Swal.fire({
                title: 'Do you want to do the transaction?',
                showDenyButton: true,
                showCancelButton: true,
                confirmButtonText: 'Save',
                denyButtonText: `Don't save`,
            }).then((result) => {
                if (result.isConfirmed) {
                    axios.patch('/api/cardTransaction', `{"cardType": "CREDIT","amount": ${this.amount},"cardNumber": "${this.cardNumber}","cardHolder": "${this.cardHolder}","cvv": "${this.cvv}","thruDate": "${this.thruDate}","description": "${this.description}","accountNumber": "${this.accountNumberFunds}"}`, { headers: { "Content-Type": "application/json" } })
                    .then(()=>
                    Swal.fire('Transfered!', '', 'success').then(() => window.location.replace("/web/Cards.html")))
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
        cardTransactionDebit() {
            Swal.fire({
                title: 'Do you want to do the transaction?',
                showDenyButton: true,
                showCancelButton: true,
                confirmButtonText: 'Save',
                denyButtonText: `Don't save`,
            }).then((result) => {
                if (result.isConfirmed) {
                    axios.patch('/api/cardTransaction', `{"cardType": "DEBIT","amount": ${this.amount},"cardNumber": "${this.cardNumber}","cardHolder": "${this.cardHolder}","cvv": "${this.cvv}","thruDate": "${this.thruDate}","description": "${this.description}"}`, { headers: { "Content-Type": "application/json" } }) 
                    .then(()=>
                    Swal.fire('Transfered!', '', 'success').then(() => window.location.replace("/web/Cards.html"))).catch(error => {
                        Swal.fire({
                            icon: 'error',
                            title: error.response.data,
                            timer: 2000,
                        })
                    })
                }
            })
        }
    },

}).mount("#app")