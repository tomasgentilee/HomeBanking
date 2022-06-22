Vue.createApp({

    data() {
        return {
            clients: [],
            client: {
            nombre: "",
            apellido: "",
            email: "",
            },
            clientEdited: {
                nombre: "",
                apellido: "",
                email: "",
            },
            completeJson: {},
        }
    },

    created() {
        axios.get("/rest/clients/current")
            .then(datos => {
                this.clients = datos.data._embedded.clients;
                this.completeJson = datos.data;
            })
    },

    computed: {

    },
    
    methods: {
        cargarTabla() {
    
            if (this.client.name !== "" && this.client.lastName !== "" && this.client.email !== "") {
                this.client = {
                    nombre: this.client.nombre,
                    apellido: this.client.apellido,
                    email: this.client.email,
                }
                axios.post("/rest/clients/current", this.client)
                    .then(client => {
                    })
                    .catch(function (error) {
                    });
                location.reload()
            }
    
        },
        editarCliente(cliente){
            let URL = cliente._links.client.href
            cliente = {
                nombre : this.clientEdited.nombre,
                apellido : this.clientEdited.apellido,
                email : this.clientEdited.email,
            }
            axios.put(URL, cliente)
            .then(client => {
                location.reload()
            })
        },
        borrarCliente(cliente){
            let URL = cliente._links.client.href
            axios.delete(URL)
            .then(client => {
                location.reload()
            });
        }

    },

}).mount("#app")