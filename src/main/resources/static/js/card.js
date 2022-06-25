Vue.createApp({

    data() {
        return {
            transactions: [],
            card: [],
            id: [],
            cardType: "",
            password: [],
            cardLimit: [],
            transactionsPdf:[],
        }
    },

    created() {
        const urlParams = new URLSearchParams(window.location.search);
        const myParam = urlParams.get('id');
        this.id = myParam

        axios.get("/api/cards/" + myParam)
            .then(datos => {
                this.card = datos.data;
                this.cardType = datos.data.cardType;
                this.cardLimit = datos.data.creditCardLimit;
                this.transactions = datos.data.transactions;
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
        showCardInformation() {
            Swal.fire({
                title: `Number: ${this.card.cardNumber}`,
                text: `Cvv: ${this.card.cvv}
                Thru date: ${this.card.thruDate}
                Available balance: ${this.card.availableBalance}
                `,
                showCancelButton: true,
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',
                cancelButtonText: 'Close',
                confirmButtonText: 'Understood'
            })
        },
        exportPDF() {
            
            this.transactions.forEach(transaction => {

              let fila = {
                  DATE: `${transaction.date}`, 
                  description: `${transaction.description}`, 
                  TYPE: `${transaction.type}`, 
                  amount: `${transaction.amount}`
              }
               this.transactionsPdf.push(fila);
           });
      
            var columns = [
              {title: "DATE", dataKey: "DATE"},
              {title: "TYPE", dataKey: "TYPE"},
              {title: "Amount", dataKey: "amount"},
              {title: "Description", dataKey: "description"},
            ];
            var doc = new jsPDF('p', 'pt');
            doc.text(`${this.card.cardNumber} transactions list`, 40, 40);
            doc.autoTable(columns, this.transactionsPdf, {
              margin: {top: 60},
            });
            doc.save('todos.pdf');
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
    },

}).mount("#app")