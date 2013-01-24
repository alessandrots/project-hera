/**
 * Created with JetBrains WebStorm.
 * User: alessandrots
 * Date: 12/01/2013
 * Time: 08:00
 *
 * Arquivo responsável por criar objetos importantes na solução do
 */


function Arvore() {
    this.tarefaOrigem   = null;
    this.tarefaDestino  = null;

    //variável que adiciona a estrutura html com base na árvore por meio de chamada recursiva
    var sb = new StringBuffer();

    /**
     * Este atributo serve para guardar as tarefas que já fizeram alguma conexão.
     * @type {Array}
     */
	this.tarefas  = new Array();
    this.root     = new Array();

	this.init = function() {
		//alert('Estou no init do Hierarquia');
	}

    this.montarArvore = function (nomeTarefaOrigem, nomeTarefaDestino) {
        this.tarefaOrigem  = new HierarquiaTarefa();
        this.tarefaDestino = new HierarquiaTarefa();

        //verificar se já existe alguma tarefa na hierarquia
        if ((this.tarefas.length > 0) &&
             ($.inArray(nomeTarefaOrigem, this.tarefas) != -1 || $.inArray(nomeTarefaDestino, this.tarefas) != -1 )){
            //reorganizando
            //console.log("reorganizarHierarquia");

            //montando as tarefas
            this.tarefaOrigem.init(nomeTarefaOrigem, 'origem');
            this.tarefaDestino.init(nomeTarefaDestino, 'destino');

            this.reorganizarHierarquia(this.tarefaOrigem, this.tarefaDestino, this.root);
        } else {
            console.log("guardando nivel 0");
            //montando nível 0(destino) e nível 1(origem)for (var i=0;i<raiz.length;i++) {
            this.tarefaOrigem.init(nomeTarefaOrigem, 'origem');
            this.tarefaDestino.init(nomeTarefaDestino, 'destino');

            //Para controlar as tarefas que já foram inseridas
            this.tarefas.push(nomeTarefaOrigem);
            this.tarefas.push(nomeTarefaDestino);

            //Adicionando tarefas de nível 0 e 1
            //this.tarefaDestino.adicionarTarefa(this.tarefaOrigem);
            this.tarefaOrigem.adicionarTarefa(this.tarefaDestino);
            //this.root.push(this.tarefaDestino);
            this.root.push(this.tarefaOrigem);
        }
    }//end of montarArvore

    /**
     *
     * @param tarefaOrigem
     * @param tarefaDestino
     * @param raiz
     */
    this.reorganizarHierarquia = function(tarefaOrigem, tarefaDestino, raiz) {
        var tarefa_ = null;
        if (raiz != null && raiz.length > 0){
            //this.raiz.each(function(indice, tarefa){

            for (var i=0;i<raiz.length;i++) {
                tarefa_ = raiz[i];
                //console.log(" no for reorganizarHierarquia tarefa_.nome = " + tarefa_.nome + " tarefaDestino.nome = " + tarefaDestino.nome);
                if (tarefa_.nome == tarefaOrigem.nome) {
                    /**
                     * se a tarefa na raiz for o destino da tarefa origem,
                     * então ela deve ser filha da tarefa já guardada.
                     *
                     * Aqui vai percorer todos os nível raiz de cada nível
                     *
                     * ao encontrar, determina um nível abaixo da hierarquia da tarefa
                     */
                    console.log("entrei no if origem = " + tarefaOrigem.nome + " destino = " + tarefaDestino.nome);
                    tarefaDestino.nivel = tarefa_.nivel + 1;
                    tarefa_.adicionarTarefa(tarefaDestino);
                    this.tarefas.push(tarefaDestino.nome);
                } else if (tarefa_.filhos != null  && tarefa_.filhos.length > 0) {
                    /**
                     * faz chamada recursiva percorrendo todos os filhos
                     * Ao chegar no final de todos os filhos, volta a percorrer o
                     * nível acima.
                     */
                    this.reorganizarHierarquia(tarefaOrigem, tarefaDestino, tarefa_.filhos);
                }
            //});
            }
        }
    }//end of reorganizarHierarquia


    this.imprimirHierarquia = function(raiz, escolha) {
        var espaco = " ";
        var tarefa = null;

        if (escolha == 1 ){
            if (raiz != null && raiz.length > 0){
                for (var k=0;k<raiz.length;k++) {
                    tarefa = raiz[k];
                    console.log("Tarefa: " + tarefa.nome + " - Nivel: " + tarefa.nivel);
                    if (tarefa.filhos != null  && tarefa.filhos.length > 0) {
                        console.log("Filho: ");
                        this.imprimirHierarquia(tarefa.filhos, 1);
                    }
                }
            } else {
                console.log("Nenhuma tarefa associada. ");
            }
        } else if (escolha == 2 ){
            for (tmp=0;tmp<raiz.length;tmp++) {
                console.log("nomes das tarefa = " + raiz[tmp]);
            }
        }

    }//end of imprimirHierarquia





    this.montarTreeView = function(raiz, sbRoot) {
        //imprime hierarquia em formato de árvore
        var sb = this.montarTreeViewPrivate(raiz);

        //alert('montarTreeView com dois parametros.');

        //adicionando a estrutura html montado com base na árvore
        sbRoot.append(sb.toString());

        //Adicionando o javascript que vai renderizar a árvore (fonte jstree)
        sbRoot.append('<script type="text/javascript">');
        sbRoot.append('$(function () {');
        sbRoot.append('$("#arvoreTarefas")');
        sbRoot.append(' .jstree({ "plugins" : ["themes","html_data","ui"] }) ');
        sbRoot.append('.bind("loaded.jstree", function (event, data) { })    ');
        sbRoot.append('.one("reopen.jstree", function (event, data) { })     ');
        sbRoot.append('.one("reselect.jstree", function (event, data) { }); ');
        sbRoot.append('});');
        sbRoot.append('</script>');

        $('#arvoreTarefas').html('');
        $('#arvoreTarefas').html(sbRoot.toString());

        return sbRoot.toString();
    }




    /**
     *
     *
     <ul>
         <li id="rhtml_1" class="jstree-open">
                <a href="#">Root node 1</a>
                <ul>
                    <li id="rhtml_2">
                        <a href="#">Child node 1</a>
                    </li>
                    <li id="rhtml_3">
                        <a href="#">Child node 2</a>
                    </li>
                </ul>
         </li>
         <li id="rhtml_4">
            <a href="#">Root node 2</a>
         </li>
     </ul>
     *
     *
     *
     * @param raiz
     */
    this.montarTreeViewPrivate = function(raiz) {
        var tarefa = null;
        sb.append('<ul>');

        if (raiz != null && raiz.length > 0){
            for (var k=0;k<raiz.length;k++) {
                tarefa = raiz[k];
                console.log("Tarefa: " + tarefa.nome + " - Nivel: " + tarefa.nivel);
                sb.append('<li id="'+ tarefa.nome + '" class="jstree-open">');
                sb.append('<a href="#">'+ tarefa.nome + '</a>');

                if (tarefa.filhos != null  && tarefa.filhos.length > 0) {
                    console.log("Filho: ");
                    //sb.append('<ul>');
                    this.montarTreeViewPrivate(tarefa.filhos);
                    //sb.append('</ul>');
                }
                sb.append('</li>');
            }
        } else {
            console.log("Nenhuma tarefa associada. ");
            sb.append('<li id=\"no_root\" class=\"jstree-open\"> Nenhuma tarefa associada.</li>');
        }

        sb.append('</ul>');

        //alert(sb.toString());
        return sb.toString();

    }//end of imprimirHierarquia

}//end of Arvore


function HierarquiaTarefa() {
    this.nome = null;
    this.filhos = [];
    this.nivel = null;

    /**
     * Inicializando o objeto.
     * @param nomeTarefa
     * @param tipo
     */
    this.init = function(nomeTarefa, tipo) {
        this.nome = nomeTarefa;
        this.nivel = (tipo == 'origem' ? 0:1);
        //alert(' construtor ');
    }

    /**
     * Adicionando tarefa filha.
     * @param tarefaOrigem
     */
    this.adicionarTarefa = function(tarefaOrigem) {
        if (this.filhos == null) {
            this.filhos = new Array();
        }

        if (this.nivel < tarefaOrigem.nivel){
            this.filhos.push(tarefaOrigem);
        }
    }//end of adicionarTarefa

}//end of Hierarquia
