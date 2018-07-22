#language:pt

Funcionalidade: Verificar Matriculas

	Contexto:
		Dado que estou na página de verificação de matriculas

	Cenário: Validar layout da tela
		Então eu vejo o titulo 'Sistema de Verificação de Matrículas'
			E eu vejo o botão 'Anexar Arquivo'
			E eu vejo o botão 'Verificar Agora' desabilitado
			E eu vejo o rodapé com link para o github

	Cenário: Validar comportamento do botão 'Anexar Arquivo'
		Quando eu clicar no botão 'Anexar Arquivo'
		Então eu vejo a janela de arquivos do sistema
		Quando eu anexar o arquivo matriculas.txt
		Então eu vejo a mensagem: 'Arquivo anexo com sucesso!' 

	Cenário: Validar comportamento do botão 'Verificar Agora'
		Quando eu clicar no botão 'Anexar Arquivo'
			 E eu anexar o arquivo matriculas.txt
		Então eu vejo o botão 'Verificar Agora' habilitado
		Quando eu clicar no botão 'Verificar Agora'
		Então eu vejo a mensagem: 'Aguade um momento...'

#Então eu recebo um aquivo xptoTratado.txt   