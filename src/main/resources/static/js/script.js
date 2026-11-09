function aoDigitar(id, formatar) {
    const campo = document.getElementById(id);
    if (!campo) return;
    campo.addEventListener('input', () => {
        campo.value = formatar(campo.value);
    });
}

const soLetras = (v) => v.replace(/[^\p{L}\s'-]/gu, '').replace(/^\s+/, '').replace(/\s{2,}/g, ' ');

const textoSimples = (v) => v.replace(/[^\p{L}\d\s,.\-/]/gu, '').replace(/^\s+/, '').replace(/\s{2,}/g, ' ');

function formatarTelefone(v) {
    const d = v.replace(/\D/g, '').slice(0, 11);
    if (d.length === 0) return '';
    if (d.length <= 2) return `(${d}`;
    if (d.length <= 6) return `(${d.slice(0, 2)}) ${d.slice(2)}`;
    if (d.length <= 10) return `(${d.slice(0, 2)}) ${d.slice(2, 6)}-${d.slice(6)}`;
    return `(${d.slice(0, 2)}) ${d.slice(2, 7)}-${d.slice(7)}`;
}

const formatarProtocolo = (v) => v.replace(/[^a-zA-Z0-9\-]/g, '').toUpperCase().slice(0, 20);

aoDigitar('inputNome', soLetras);
aoDigitar('inputTelefone', formatarTelefone);
aoDigitar('inputNecessidade', textoSimples);
aoDigitar('inputProtocolo', formatarProtocolo);

const descricao = document.getElementById('textareaDescricao');
const contador = document.getElementById('contadorDescricao');
if (descricao && contador) {
    const atualizar = () => { contador.textContent = `${descricao.value.length}/${descricao.maxLength}`; };
    descricao.addEventListener('input', atualizar);
    atualizar();
}
