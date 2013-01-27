/**
 * Created with JetBrains WebStorm.
 * User: alessandrots
 * Date: 21/01/13
 * Time: 13:38
 * To change this template use File | Settings | File Templates.
 */

/**
 * Implementação da String Buffer.
 *
 * http://www.giantgeek.com/blog/?p=247
 *
 * Utilizacao:
 *
 * var sb = new StringBuffer();
 *  sb.append(x1);
 *  sb.append(x2);
 *  sb.append(x3);
 *  return sb.toString();
 *
 * @constructor
 */
function StringBuffer()
{
    this.buffer = [];
}

StringBuffer.prototype.append = function(string)
{
    this.buffer.push(string);
    return this;
}

StringBuffer.prototype.toString = function()
{
    return this.buffer.join("");
}

/******************************************/

/**
 * retornarProximoID - função de teste
 *
 * TODO definir uma chave para o nome de cada div.
 *
 * Tem que pensar que as divs vão ser geradas por usuários diferentes.
 *
 */
function retornarProximoID(proximoID) {
    //alert(proximoID);
    if (proximoID == 0){
        proximoID = 1000
    }
    proximoID = proximoID +1;
    return proximoID;
}