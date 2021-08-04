package com.core.api.pagamento.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import java.util.Date;

@Entity
@JsonTypeName("pagamentoComBoleto")
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class PagamentoComBoleto extends Pagamento {

    private static final long serialVersionUID = 1L;

    @JsonFormat(pattern = "dd/mm/yyyy")
    private Date dataVencimento;

    @JsonFormat(pattern = "dd/mm/yyyy")
    private Date dataPagamento;

}
