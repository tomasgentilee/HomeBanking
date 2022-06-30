Vue.createApp({

  data() {
      return {
        todos: [
          {DATE: 'todo 1', description: 'description 1', TYPE: 'todas', balance: '10 pe'},
        ]
      }
  },

  methods: {
    exportPDF() {

      array.forEach(transaction => {
  
        let fila = `DATE: ${this.date}, description: ${this.description}, TYPE: ${this.type}, balance: ${this.balance}`;
     
         todos.push(fila);
     });


      var columns = [
        {title: "DATE", dataKey: "DATE"},
        {title: "TYPE", dataKey: "TYPE"},
        {title: "Balance", dataKey: "balance"},
        {title: "Description", dataKey: "description"},
      ];
      var doc = new jsPDF('p', 'pt');
      doc.text(`${this.account} transactions list from ${this.fromDate} to ${this.toDate}`, 40, 40);
      doc.autoTable(columns, this.todos, {
        margin: {top: 60},
      });
      doc.save('todos.pdf');
    },    
  },

}).mount("#app")