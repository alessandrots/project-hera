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
    this.ultimoNivel = -1;

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
            //montando nível 0(destino) e nível 1(origem)
            this.tarefaOrigem.init(nomeTarefaOrigem, 'origem');
            this.tarefaDestino.init(nomeTarefaDestino, 'destino');

            //Para controlar as tarefas que já foram inseridas
            this.tarefas.push(nomeTarefaOrigem);
            this.tarefas.push(nomeTarefaDestino);

            //Adicionando tarefas de nível 0 e 1
            //this.tarefaDestino.adicionarTarefa(this.tarefaOrigem);
            this.tarefaOrigem.adicionarTarefa(this.tarefaDestino);
            this.root.push(this.tarefaOrigem);
        }
    }//end of montarArvore

    /**
     * Funcionamento da rotina:
     * if tarefaOrigem.nome:
     *      se a tarefa dentro do root, for a origem da nova tarefa criada e ela NÂO contiver a tarefa destino,
     *      então esta nova tarefa deve ser filha da tarefa já guardada.
     *      Aqui vai percorer todos os nível raiz de cada nível
     *      ao encontrar, determina um nível ABAIXO da hierarquia da tarefa
     *
     * else tarefaDestino.nome:
     *      se a tarefa dentro do root, for o destino da nova tarefa criada,
     *      então esta nova tarefa deve ser pai da tarefa já guardada.
     *      Aqui vai percorer todos os nível raiz de cada nível
     *      ao encontrar, determina um nível ACIMA da hierarquia da tarefa
     *
     * else tarefa_.filhos:
     *      faz chamada recursiva percorrendo todos os filhos
     *      Ao chegar no final de todos os filhos, volta a percorrer o nível acima.
     *
     * @param tarefaOrigem
     * @param tarefaDestino
     * @param raiz
     */
    this.reorganizarHierarquia = function(tarefaOrigem, tarefaDestino, raiz) {
        var tarefa_ = null;
        var retorno = false;

        if (raiz != null && raiz.length > 0){
            //this.raiz.each(function(indice, tarefa){

            for (var i=0;i<raiz.length;i++) {
                //recuperando a tarefa dentro do array
                tarefa_ = raiz[i];

                if (tarefa_.nome == tarefaOrigem.nome) {
                    //Para evitar duplicidades de tarefas já adicionadas
                    retorno = this.verificarExistenciaTarefaAdicionada(tarefaDestino.nome, tarefa_.filhos);

                    //só adiciona um elemento filho se ele já não existir
                    if (!retorno) {
                        console.log("entrei no if origem = " + tarefaOrigem.nome + " destino = " + tarefaDestino.nome);
                        tarefaDestino.nivel = tarefa_.nivel + 1;
                        tarefa_.adicionarTarefa(tarefaDestino);
                        this.tarefas.push(tarefaDestino.nome);
                    }
                } else if (tarefa_.nome == tarefaDestino.nome) {
                    console.log("entrei no else origem = " + tarefaOrigem.nome + " destino = " + tarefaDestino.nome);
                    tarefaOrigem.nivel = tarefa_.nivel - 1;
                    tarefaOrigem.adicionarTarefa(tarefa_);
                    this.tarefas.push(tarefaOrigem.nome);
                    this.root.push(this.tarefaOrigem);
                } else if (tarefa_.filhos != null  && tarefa_.filhos.length > 0) {
                    console.log("entrei na chamada recursiva: reorganizarHierarquia");
                    this.reorganizarHierarquia(tarefaOrigem, tarefaDestino, tarefa_.filhos);
                }
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


    /**
     *
     * @param raiz
     * @param sbRoot
     * @return {*}
     */
    this.montarTreeView = function(raiz, sbRoot) {
        $('#arvoreTarefas').html('');

        //imprime hierarquia em formato de árvore
        var sb = this.montarTreeViewPrivate(raiz);

        //alert('montarTreeView com dois parametros.');

        //adicionando a estrutura html montado com base na árvore
        sbRoot.append(sb.toString());

        //Adicionando o javascript que vai renderizar a árvore (fonte jstree)
        sbRoot.append('<script type="text/javascript">');
        sbRoot.append(' $(function () {');
        sbRoot.append(' $("#arvoreTarefas")');
        sbRoot.append('  .jstree({ "plugins" : ["themes","html_data","ui"] }) ');
        sbRoot.append(' .bind("loaded.jstree", function (event, data) { })    ');
        sbRoot.append(' .one("reopen.jstree", function (event, data) { })     ');
        sbRoot.append(' .one("reselect.jstree", function (event, data) { }); ');
        sbRoot.append(' });');
        sbRoot.append('</script>');


        $('#arvoreTarefas').html(sbRoot.toString());

        return sbRoot.toString();
    }

    /**
     *Montando a seguinte estrutura:
     * <ul>
     *      <li id="rhtml_1" class="jstree-open">
     *          <a href="#">Root node 1</a>
     *          <ul>
     *              <li id="rhtml_2">
     *                  <a href="#">Child node 1</a>
     *              </li>
     *              <li id="rhtml_3">
     *                  <a href="#">Child node 2</a>
     *              </li>
     *          </ul>
     *     </li>
     *     <li id="rhtml_4">
     *         <a href="#">Root node 2</a>
     *     </li>
     * </ul>
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

        return sb.toString();
    }//end of imprimirHierarquia


    /**
     * Verificar existencia de Tarefa já adicionada.
     *
     * @param nomeTarefa
     * @param arrayTarefas
     * @return {Boolean}
     */
    this.verificarExistenciaTarefaAdicionada = function(nomeTarefa, arrayTarefas) {
        var retorno = false;

        if (arrayTarefas.length >0) {
            for (var k=0;k<arrayTarefas.length;k++) {
                tarefaTmp_ = arrayTarefas[k];
                if (nomeTarefa == tarefaTmp_.nome ){
                    retorno = true;
                    break;
                }
            }
        }

        return retorno;
    }


    this.setarUltimoNivel = function(nivel_) {
        if (nivel_ > this.ultimoNivel){
            this.ultimoNivel = nivel_;
        }
    }

    this.obterUltimoNivel = function() {
        return  this.ultimoNivel;
    }

    /*setarUltimoNivel
    * Reposiciona os objetos numa hierarquia horizontal e vertical. Faz um offset em cada tarefa.
    *
    */
    this.reposicionarObjetos = function() {
        var hierarquiaControle = new HierarquiaTarefa();
        var colecaoPorNivel    = new Array();
        var tarefa             = null;
        var arrayNiveis        = new Array();
        var referenciaPosicao  = new ReferenciaPosicao();
        var referenciaPosicao2 = null;
        var newPosition        = 0;
        var arrayRefPosicaoPorNivel = new Array();

        //reposicionamento dos objetos com chamada recursiva
        this.reposicionarObjetosPrivate(hierarquiaControle, colecaoPorNivel, this.root, arrayNiveis);

        //setando o maior nível da arvore em memória
        this.setarUltimoNivel(arrayNiveis[arrayNiveis.length -1]);
        console.log(' Ultimo nivel ='  + this.obterUltimoNivel());

        //gerando um array de ReferenciaPosicao de acordo com o maximo de niveis daquela árvore
        referenciaPosicao.recuperarArrayReferenciaPosicaoPorNivel(this.obterUltimoNivel(), arrayRefPosicaoPorNivel);

        //Recuperando o array de arrays de tarefas
        colecaoPorNivel = hierarquiaControle.colecaoMesmoNivel;

        if (colecaoPorNivel.length >0) {
            for (var k=0;k < colecaoPorNivel.length;k++) {
                tmpColecao = colecaoPorNivel[k];

                //recuperando para cada nível a posição da primeira linha horizontal
                referenciaPosicao2 = arrayRefPosicaoPorNivel[k];

                //percorrendo as tarefas verticalmente (variando o top)
                for (var i=0;i < tmpColecao.length;i++) {
                    tarefa = tmpColecao[i];
                    referenciaPosicao2.altura = $('#' + tarefa.nome).height();
                    //console.log('Tarefa -> NOME = '+ tarefa.nome);

                    //mudando só o top
                    if (i > 0){
                        newPosition = referenciaPosicao2.obterNovaPosicaoTopPorNivel();
                        console.log('newPosition = '+ newPosition);
                        referenciaPosicao2.top = newPosition;
                    }
                    console.log('top: '+ referenciaPosicao2.top + ' left: ' + referenciaPosicao2.left);

                    //reposicionando os elementos
                    $('#' + tarefa.nome).offset({top: referenciaPosicao2.top, left: referenciaPosicao2.left});
                }
            }
        }
    }//end of reposicionarObjetos

    /**
     * Gera uma estrutura de array de arrays de tarefas, no seguinte formato:
     * [
     *  [t_1,t_2,t_3], //nível 0
     *  [t_4,t_5,t_6], //nível 1
     *  [t_7,t_8,t_9], //nível 2
     *  ...
     * ]
     *
     * Este método faz chamada recursiva.
     *
     * @param hierarquiaControle: o objeto que vai guardar a coleção de arrays
     * @param colecaoPorNivel: o array de arrays
     * @param raiz: as tarefas no formato de árvore
     * @param arrNiveis: um array de inteiros, cada inteiro representa um nível [0,1,2...]
     */
    this.reposicionarObjetosPrivate = function(hierarquiaControle,colecaoPorNivel,raiz, arrNiveis) {
        var tarefa = null;

        if (raiz != null && raiz.length > 0){
            for (var k=0;k<raiz.length;k++) {
                tarefa = raiz[k];

                //o nivel x ja existe no array de niveis
                if ($.inArray(tarefa.nivel, arrNiveis) != -1) {
                    colecaoPorNivel = hierarquiaControle.colecaoMesmoNivel;

                    // colecao do nivel específico
                    tmpColecao = colecaoPorNivel[tarefa.nivel];

                    //atualizou o subarray de tarefas
                    tmpColecao.push(tarefa);
                    colecaoPorNivel[tarefa.nivel] = tmpColecao;
                    hierarquiaControle.colecaoMesmoNivel= colecaoPorNivel;
                } else {
                    tmpColecao = new Array();
                    tmpColecao.push(tarefa);
                    colecaoPorNivel[tarefa.nivel] = tmpColecao;
                    hierarquiaControle.colecaoMesmoNivel= colecaoPorNivel;
                    arrNiveis.push(tarefa.nivel);
                }

                if (tarefa.filhos != null  && tarefa.filhos.length > 0) {
                    this.reposicionarObjetosPrivate(hierarquiaControle,colecaoPorNivel,tarefa.filhos, arrNiveis);
                }
            }
        }
    }//end of reposicionarObjetosPrivate


}//end of Arvore

/******************************************/

function HierarquiaTarefa() {
    this.nome = null;
    this.filhos = [];
    this.nivel = null;
    this.colecaoMesmoNivel = [];

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

/******************************************/

function ReferenciaPosicao() {
    this.nome = null;
    this.refPosicaoTarefa = null;
    this.left=0;
    this.top=0;
    this.altura=0;

    const leftInicial = 30;
    const topInicial  = 34;
    const variacao    = 30;

    /**
     *  Mudando a posição
     *  Havendo mais de uma tarefa, Pega a última tarefa, para reposicionar a mesma,
     *  mantendo o mesmo left e variando o top com base na altura mais um valor constante,
     *  a partir do último componente div.
     *
     * @param nomeTarefa
     */
    this.definirPosicaoCriacao = function(nomeTarefa) {
        var coordenadas =null;
        var altura = null;
        this.nome = nomeTarefa;

        if  (this.refPosicaoTarefa == null) {
            // para a primeira tarefa
            this.refPosicaoTarefa = new ReferenciaPosicao();
            this.refPosicaoTarefa.left   = leftInicial;
            this.refPosicaoTarefa.top    = topInicial;
            this.refPosicaoTarefa.altura =$('#' + nomeTarefa).height();
        } else {
            //a partir do segundo elemento
            var newPosition = this.refPosicaoTarefa.top +
                              Math.round(this.refPosicaoTarefa.altura) +
                              variacao;

            //mudando só o top
            this.refPosicaoTarefa.top= newPosition;
        }

        //reposicionando os elementos
        $('#' + nomeTarefa).offset({top: this.refPosicaoTarefa.top, left: this.refPosicaoTarefa.left});
    }


    this.obterNovaPosicaoTopPorNivel = function() {
        return this.top + Math.round(this.altura) + variacao;
    }

    /**
     * 2) Ao clicar no botão de ordenação da hierarquia, vai rolar o seguinte:
     * 2.1) A hierarquia ZERO, vai estar no TOP: 100/LEFT: 150
     * 2.2)A hierarquia UM, vai estar no TOP: 100/LEFT: 300, então todo o left vai variar de 100% e todo o top vai variar de 110%.
     *
     * @param ultimoNivel
     */
    this.recuperarArrayReferenciaPosicaoPorNivel = function(ultimoNivel, arrayRefPosicaoPorNivel) {
        for (var k=0;k<ultimoNivel + 1;k++) {
            if (k == 0){
                referenciaPosicaoTarefa = new ReferenciaPosicao();
                referenciaPosicaoTarefa.left   = 150;
                referenciaPosicaoTarefa.top    = 100;
                arrayRefPosicaoPorNivel.push(referenciaPosicaoTarefa);
            } else {
                tmpRefPosicao = arrayRefPosicaoPorNivel[k-1];
                newReferenciaPosicaoTarefa = new ReferenciaPosicao();
                newReferenciaPosicaoTarefa.left = tmpRefPosicao.left + 150;
                newReferenciaPosicaoTarefa.top = tmpRefPosicao.top;
                arrayRefPosicaoPorNivel.push(newReferenciaPosicaoTarefa);
            }
        }
    }
}

