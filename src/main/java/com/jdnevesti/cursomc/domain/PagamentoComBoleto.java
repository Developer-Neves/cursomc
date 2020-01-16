package com.jdnevesti.cursomc.domain;

import java.util.Date;

import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jdnevesti.cursomc.domain.Enums.EstadoPagamento;

@Entity
public class PagamentoComBoleto extends Pagamento {
	private static final long serialVersionUID = 1L;
	
	@JsonFormat(pattern="dd/mm/yyyy")
	private Date dataVencimento;
	
	@JsonFormat(pattern="dd/mm/yyyy")
	private Date datePagamento;
	
	public PagamentoComBoleto() {		
	}

	public PagamentoComBoleto(Integer id, EstadoPagamento estado, Pedido pedido, Date dataVencimento, Date dataPagamento) {
		super(id, estado, pedido);
		this.dataVencimento = dataVencimento;
		this.datePagamento = dataPagamento;
	}

	public Date getDataVencimento() {
		return dataVencimento;
	}

	public void setDataVencimento(Date dataVencimento) {
		this.dataVencimento = dataVencimento;
	}

	public Date getDatePagamento() {
		return datePagamento;
	}

	public void setDatePagamento(Date datePagamento) {
		this.datePagamento = datePagamento;
	}
	
	
}