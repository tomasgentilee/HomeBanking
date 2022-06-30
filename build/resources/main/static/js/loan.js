Vue.createApp({

    data() {
        return {
            loan: [],
            payments: [],
        }
    },

    created() {
        const urlParams = new URLSearchParams(window.location.search);
        const myParam = urlParams.get('id');
        this.id = myParam

        axios.get("/api/clientLoans/" + myParam)
            .then(datos => {
                this.loan = datos.data;
                this.payment = datos.data.payment;
            })
    },

    computed: {
    },

    methods: {
        deletCard() {
            (axios.patch('/api/clients/current/cards', `password=${this.password}&id=${this.id}`, { headers: { 'content-type': 'application/x-www-form-urlencoded' } }).then(
                Swal.fire('Deleted!', '', 'success').then(() => window.location.replace("/web/cards.html"))))
                .catch(error => {
                    Swal.fire({
                        icon: 'error',
                        title: error.response.data,
                        timer: 2000,
                    })
                })

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
        logout() {
            axios.post('/api/logout').then(location.reload()).then(() => window.location.replace("/web/home.html"))
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
    },

}).mount("#app")