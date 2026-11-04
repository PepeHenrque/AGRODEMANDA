-- Carga inicial de dados para demonstracao
INSERT INTO tb_demandas (protocolo, nome_solicitante, telefone, comunidade, tipo_servico, descricao, status, data_criacao)
VALUES 
('AGRO-2026-0001', 'Jose Severino da Silva', '(81) 99876-1234', 'Magana', 'Preparo de Solo', 'Necessidade de trator para gradear 2 hectares para plantio de milho.', 'PENDENTE', CURRENT_TIMESTAMP()),
('AGRO-2026-0002', 'Maria das Dores Santos', '(81) 98765-4321', 'Barra da Cruz', 'Manutenção de Estradas', 'Trecho da estrada principal com buracos dificultando escoamento da producao.', 'EM_ATENDIMENTO', CURRENT_TIMESTAMP()),
('AGRO-2026-0003', 'Antonio Carlos Bezerra', '(81) 99123-4567', 'Cacimba de Baixo', 'Abastecimento e Recursos Hídricos', 'Limpeza e desassoreamento de pequeno barreiro comunitario.', 'CONCLUIDO', CURRENT_TIMESTAMP());
