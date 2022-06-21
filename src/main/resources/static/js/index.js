Vue.createApp({

    data() {
        return {
            email: "",
            password: "",
            registerEmail: "",
            registerName: "",
            registerLastName: "",
            registerPassword: "",
        }
    },

    created() {
    },

    computed: {

    },
    
    methods: {
        login(){
            axios.post('/api/login',`email=${this.email}&password=${this.password}`,{headers:{'content-type':'application/x-www-form-urlencoded'}})
            .then(()=> window.location.replace("/web/home.html"))
        },
        logout(){
            axios.post('/api/logout').then(response => console.log('signed out!!!'))
        },
        register(){
            axios.post('/api/clients',`name=${this.registerName}&lastName=${this.registerLastName}&email=${this.registerEmail}&password=${this.registerPassword}`,{headers:{'content-type':'application/x-www-form-urlencoded'}})
            .then(()=>{
                this.email = this.registerEmail
                this.password = this.registerPassword
                this.login();
            }).catch(error=>(console.warn(error)))
        },
        switcher(){
            const sign_in_btn = document.querySelector("#sign-in-btn");
            const sign_up_btn = document.querySelector("#sign-up-btn");
            const container = document.querySelector(".container");

            sign_up_btn.addEventListener("click", () => {
            container.classList.add("sign-up-mode");
            });

            sign_in_btn.addEventListener("click", () => {
            container.classList.remove("sign-up-mode");
        });
	}},

}).mount("#app")