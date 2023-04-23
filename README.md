[ ] 1. Na classe ContasActivity, já há um RecyclerView que usa um Adapter para mostrar uma lista de contas, mas os dados ainda não estão sendo recuperados do banco de dados. Dica: use o atributo contas (com tipo LiveData<List<Conta>>) de ContaViewModel para fazer isto;

[ ] 2. Na classe ContaViewHolder, a imagem que é mostrada na lista de contas não é alterada caso o saldo esteja negativo. Inclua o código correspondente na função bindTo;

[ ] 3. Na classe ContaViewHolder, o Intent criado para enviar o usuário para a tela EditarContaActivity, não inclui o número da conta, informação essencial para recuperar os dados da conta na tela a ser aberta;

[ ] 4. Na classe AdicionarContaActivity, inclua a funcionalidade de validar as informações digitadas (ex.: nenhum campo em branco, saldo é um número) antes de criar um objeto Conta no banco de dados. Implemente também o código que usa ContaViewModel para armazenar o objeto no banco de dados;

[ ] 5. Na classe ContaDAO, inclua métodos para atualizar e remover contas no banco de dados, além de três métodos para buscar (1) pelo número da conta, (2) pelo nome do cliente e (3) pelo CPF do cliente;

[ ] 6. Na classe ContaRepository, implemente o corpo dos métodos para atualizar e remover contas no banco de dados, além dos métodos para buscar pelo número da conta, pelo nome do cliente e pelo CPF do cliente. Estes métodos devem usar os métodos criados na classe ContaDAO no passo anterior;

[ ] 7. Na classe ContaViewModel, inclua métodos para atualizar e remover contas no banco de dados, além de um método para buscar pelo número da conta. Estes métodos devem usar os métodos criados na classe ContaRepository no passo anterior;

[ ] 8. Na classe EditarContaActivity, inclua a funcionalidade de recuperar as informações da conta de acordo com o número passado pelo Intent recebido pela Activity. Atualize os campos do formulário de acordo;

[ ] 9. Na classe EditarContaActivity, inclua a funcionalidade de validar as informações digitadas (ex.: nenhum campo em branco, saldo é um número) antes de atualizar a Conta no banco de dados. Implemente também o código que usa ContaViewModel para armazenar o objeto  atualizado no banco de dados;

[ ] 10. Na classe EditarContaActivity, implemente o código que usa ContaViewModel para remover o objeto do banco de dados;

[ ] 11. Na classe BancoViewModel, inclua métodos para realizar as operações de transferir, creditar, e debitar, bem como métodos para buscar pelo número da conta, pelo nome do cliente e pelo CPF do cliente. Estes métodos devem usar os métodos de ContaRepository criados em passos anteriores;

[ ] 12. Nas classes DebitarActivity, CreditarActivity, e TransferirActivity, implementar validação dos números das contas e do valor da operação, antes de efetuar a operação correspondente à tela. Você é livre para usar outro widget se preferir, como Spinner ou AutoCompleteTextView, por exemplo;

[ ] 13. Na classe PesquisarActivity, implementar o código que faz busca no banco de dados de acordo com o tipo de busca escolhido pelo usuário (ver RadioGroup tipoPesquisa);

[ ] 14. Na classe PesquisarActivity, ao realizar uma busca, atualizar o RecyclerView com os resultados da busca na medida que encontrar algo;

[ ] 15. Na classe MainActivity, mostrar o valor total de dinheiro armazenado no banco na tela principal. Este valor deve ser a soma de todos os saldos das contas armazenadas no banco de dados.

(Daqui em diante - Opcional)
[ ] Opcional 1. Incluir as telas para gerenciamento de Clientes e ajustar implementação do BD para refletir relacionamento em que toda Conta tem 1 Cliente, mas 1 Cliente pode ter mais de 1 Conta - neste caso, ao adicionar uma conta tem que verificar se o Cliente é válido (se é um Cliente já existente no banco);

[ ] Opcional 2. Extrair as strings e traduzir a aplicação para outra língua;

[ ] Opcional 3. Fazer melhorias de UI na aplicação.