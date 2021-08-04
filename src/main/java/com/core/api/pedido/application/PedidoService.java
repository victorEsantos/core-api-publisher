package com.core.api.pedido.application;

import com.core.api.cliente.application.ClienteService;
import com.core.api.pagamento.application.BoletoService;
import com.core.api.pagamento.domain.PagamentoRepository;
import com.core.api.pedido.domain.ItemPedidoRepository;
import com.core.api.pedido.domain.PedidoRepository;
import com.core.api.security.service.EmailService;
import com.core.api.security.service.UserService;
import com.core.api.security.service.exceptions.AuthorizationException;
import com.core.api.security.service.exceptions.ObjectNotFoundException;
import com.core.api.cliente.domain.Cliente;
import com.core.api.pedido.domain.ItemPedido;
import com.core.api.pagamento.domain.PagamentoComBoleto;
import com.core.api.pedido.domain.Pedido;
import com.core.api.pagamento.domain.enums.EstadoPagamento;
import com.core.api.produto.application.ProdutoService;
import com.core.api.security.userss.UserSS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class PedidoService
{
	@Autowired
	private PedidoRepository pedidoRepository;

	@Autowired
	private BoletoService boletoService;

	@Autowired
	private PagamentoRepository pagamentoRepository;

	@Autowired
	private ProdutoService produtoService;

	@Autowired
	private ItemPedidoRepository itemPedidoRepository;

	@Autowired
	private ClienteService clienteService;

	@Autowired
	private EmailService emailService;

	public Pedido buscar(Integer id)
	{
		Optional<Pedido> obj = pedidoRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
			"Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
	}

	public Pedido insert(Pedido obj) {
		obj.setId(null);
		obj.setInstante(new Date());
		obj.setCliente(clienteService.find(obj.getCliente().getId()));
		obj.getPagamento().setEstadoPagamento(EstadoPagamento.PENDENTE);
		obj.getPagamento().setPedido(obj);
		if (obj.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
			boletoService.preencherPagamentoComBoleto(pagto, obj.getInstante());
		}
		obj = pedidoRepository.save(obj);
		pagamentoRepository.save(obj.getPagamento());
		for (ItemPedido ip : obj.getItens()) {
			ip.setDesconto(0.0);
			ip.setProduto(produtoService.buscar(ip.getProduto().getId()));
			ip.setPreco(ip.getProduto().getPreco());
			ip.setPedido(obj);
		}
		itemPedidoRepository.saveAll(obj.getItens());

		//System.out.println(obj);
		//emailService.sendOrderConfirmation(obj);
		emailService.sendOrderConfirmationHtmlEmail(obj);
		return obj;
	}

	public Page<Pedido> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		UserSS user = UserService.authenticated();
		if(user == null)
			throw new AuthorizationException("Acesso negado");

		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
		Cliente cliente = clienteService.find(user.getId());
		return pedidoRepository.findByCliente(cliente, pageRequest);
	}

}
