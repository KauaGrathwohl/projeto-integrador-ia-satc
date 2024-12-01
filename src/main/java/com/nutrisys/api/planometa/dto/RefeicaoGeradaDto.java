package com.nutrisys.api.planometa.dto;

import com.nutrisys.api.enums.TipoRefeicao;

import java.math.BigDecimal;
import java.util.List;

public class RefeicaoGeradaDto {
    private TipoRefeicao tipoRefeicao;
    private BigDecimal calorias;
    private BigDecimal proteinas;
    private BigDecimal carboidratos;
    private BigDecimal gordura;
    private List<ReceitaGeradaDto> receitas;

    public TipoRefeicao getTipoRefeicao() {
        return tipoRefeicao;
    }

    public void setTipoRefeicao(TipoRefeicao tipoRefeicao) {
        this.tipoRefeicao = tipoRefeicao;
    }

    public BigDecimal getCalorias() {
        return calorias;
    }

    public void setCalorias(BigDecimal calorias) {
        this.calorias = calorias;
    }

    public BigDecimal getProteinas() {
        return proteinas;
    }

    public void setProteinas(BigDecimal proteinas) {
        this.proteinas = proteinas;
    }

    public BigDecimal getCarboidratos() {
        return carboidratos;
    }

    public void setCarboidratos(BigDecimal carboidratos) {
        this.carboidratos = carboidratos;
    }

    public BigDecimal getGordura() {
        return gordura;
    }

    public void setGordura(BigDecimal gordura) {
        this.gordura = gordura;
    }

    public List<ReceitaGeradaDto> getReceitas() {
        return receitas;
    }

    public void setReceitas(List<ReceitaGeradaDto> receitas) {
        this.receitas = receitas;
    }
}
