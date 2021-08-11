package com.core.api.security.service;

import com.core.api.categoria.domain.CategoriaRepository;
import com.core.api.cliente.domain.ClienteRepository;
import com.core.api.endereco.domain.*;
import com.core.api.pagamento.domain.PagamentoRepository;
import com.core.api.pedido.domain.ItemPedidoRepository;
import com.core.api.pedido.domain.PedidoRepository;
import com.core.api.produto.domain.ProdutoRepository;
import com.core.api.categoria.domain.Categoria;
import com.core.api.cliente.domain.Cliente;
import com.core.api.pagamento.domain.enums.EstadoPagamento;
import com.core.api.cliente.domain.enums.Perfil;
import com.core.api.cliente.domain.enums.TipoCliente;
import com.core.api.pagamento.domain.PagamentoComBoleto;
import com.core.api.pagamento.domain.PagamentoComCartao;
import com.core.api.pedido.domain.ItemPedido;
import com.core.api.pedido.domain.Pedido;
import com.core.api.produto.domain.Produto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class DBService
{

	@Autowired
	private BCryptPasswordEncoder pe;

	@Autowired
	private CategoriaRepository categoriaRepository;

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private EnderecoRepository enderecoRepository;

	@Autowired
	private PedidoRepository pedidoRepository;

	@Autowired
	private PagamentoRepository pagamentoRepository;

	@Autowired
	private ItemPedidoRepository itemPedidoRepository;

	public void instantiateTestDatabase() throws ParseException
	{
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		Categoria cat3 = new Categoria(null, "Cama Mesa e Banho");
		Categoria cat4 = new Categoria(null, "Esportes");
		Categoria cat5 = new Categoria(null, "Saude");
		Categoria cat6 = new Categoria(null, "Escola");
		Categoria cat7 = new Categoria(null, "Limpeza");

		var p1 = Produto.builder().nome("computador").preco(2000.0).build();
		var p2 = Produto.builder().nome("impressora").preco(800.0).build();
		var p3 = Produto.builder().nome("mouse").preco(80.0).build();
		var p4 = Produto.builder().nome("mesa de escritorio").preco(2000.0).build();
		var p5 = Produto.builder().nome("toalha").preco(800.0).build();
		var p6 = Produto.builder().nome("colcha").preco(80.0).build();
		var p7 = Produto.builder().nome("tv true color").preco(2000.0).build();
		var p8 = Produto.builder().nome("roçadeira").preco(800.0).build();
		var p9 = Produto.builder().nome("abajour").preco(80.0).build();
		var p10 = Produto.builder().nome("Pendente").preco(00.0).build();
		var p11 = Produto.builder().nome("Shampoo").preco(8.0).build();


		//substituir setProdutos(...) por setProdutos(...)
		cat1.setProdutos(Arrays.asList(p1, p2, p3));
		cat2.setProdutos(Arrays.asList(p2, p4));
		cat3.setProdutos(Arrays.asList(p5, p6));
		cat4.setProdutos(Arrays.asList(p1, p2, p3, p7));
		cat5.setProdutos(Arrays.asList(p8));
		cat6.setProdutos(Arrays.asList(p9, p10));
		cat7.setProdutos(Arrays.asList(p11));

		p1.setCategorias(Arrays.asList(cat1, cat4));
		p2.setCategorias(Arrays.asList(cat1, cat2, cat4));
		p3.setCategorias(Arrays.asList(cat1, cat4));
		p4.setCategorias(Arrays.asList(cat2));
		p5.setCategorias(Arrays.asList(cat3));
		p6.setCategorias(Arrays.asList(cat3));
		p7.setCategorias(Arrays.asList(cat4));
		p8.setCategorias(Arrays.asList(cat5));
		p9.setCategorias(Arrays.asList(cat6));
		p10.setCategorias(Arrays.asList(cat6));
		p11.setCategorias(Arrays.asList(cat7));

		categoriaRepository.saveAll(Arrays.asList(cat1, cat2, cat3, cat4, cat5, cat6, cat7));
		produtoRepository.saveAll(Arrays.asList(p1, p2, p3, p4, p5 , p6, p7, p8, p9, p10, p11));

		Estado est1 = Estado.MG;
		Estado est2 = Estado.SP;

		Cidade c1 = new Cidade(null, "Uberlandia", est1);
		Cidade c2 = new Cidade(null, "São Paulo", est2);
		Cidade c3 = new Cidade(null, "Campinas", est2);

		Cliente cli1 = new Cliente(null, "Maria silva", "victor.sk8.santos@gmail.com", "19344569704",
			TipoCliente.PESSOAFISICA, pe.encode("123"));
		cli1.getTelefones().addAll(Arrays.asList("12314521412", "132214124321"));


		Cliente cli2 = new Cliente(null, "João silva", "victor.sk8.santos2@gmail.com", "32792460083",
				TipoCliente.PESSOAFISICA, pe.encode("123"));
		cli2.addPerfil(Perfil.ADMIN);
		cli2.getTelefones().addAll(Arrays.asList("5234234", "234234"));

		Endereco e1 = new Endereco(null, "rua flores", "300", "apto 303", "Jardim", "432523523", cli1,
			c1);
		Endereco e2 = new Endereco(null, "Av amtos", "105", "sala 12", "centro", "1234534", cli1, c2);

		Endereco e3 = new Endereco(null, "Av floriano", "777", "", "sul", "98765", cli2, c2);

		cli1.setEnderecos(Arrays.asList(e1, e2));
		cli2.setEnderecos(Arrays.asList(e3));

		cidadeRepository.saveAll(Arrays.asList(c1,c2,c3));
		clienteRepository.saveAll(Arrays.asList(cli1, cli2));
		enderecoRepository.saveAll(Arrays.asList(e1, e2, e3));

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

		var ped1 = Pedido.builder()
				.id(null)
				.instante(sdf.parse("30/09/2020 10:32"))
				.cliente(cli1)
				.enderecoDeEntrega(e1)
				.build();

		var ped2 = Pedido.builder()
				.id(null)
				.instante(sdf.parse("10/10/2020 08:22"))
				.cliente(cli1)
				.enderecoDeEntrega(e2)
				.build();


		var pagto1 = PagamentoComCartao
				.builder()
				.id(null)
				.estadoPagamento(EstadoPagamento.QUITADO)
				.pedido(ped1)
				.numeroDeParcelas(6)
				.build();
		ped1.setPagamento(pagto1);

		var pagto2 = PagamentoComBoleto
				.builder()
				.id(null)
				.estadoPagamento(EstadoPagamento.PENDENTE)
				.pedido(ped2)
				.dataVencimento(sdf.parse("20/10/2020 00:00"))
				.dataPagamento(null)
				.build();

		ped2.setPagamento(pagto2);

		cli1.setPedidos(Arrays.asList(ped1, ped2));

		pedidoRepository.saveAll(Arrays.asList(ped1, ped2));
		pagamentoRepository.saveAll(Arrays.asList(pagto1, pagto2));

		ItemPedido ip1 = new ItemPedido(ped1, p1, 0.00, 1, 2000.00);
		ItemPedido ip2 = new ItemPedido(ped1, p3, 0.00, 2, 80.00);
		ItemPedido ip3 = new ItemPedido(ped2, p2, 100.00, 1, 800.00);

		ped1.setItens(Stream.of(ip1, ip2).collect(Collectors.toSet()));
		ped2.setItens(Stream.of(ip3).collect(Collectors.toSet()));

		p1.setItens(Stream.of(ip1).collect(Collectors.toSet()));
		p2.setItens(Stream.of(ip3).collect(Collectors.toSet()));
		p3.setItens(Stream.of(ip2).collect(Collectors.toSet()));

		itemPedidoRepository.saveAll(Arrays.asList(ip1,ip2,ip3));
	}
}
