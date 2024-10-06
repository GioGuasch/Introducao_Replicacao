# Códigos da Replicação Científica

Este diretório apresenta os códigos da oficina de replicação e os dados brutos e finais.

## Artigo-base da replicação e análise replicada

O artigo [BitTorrent traffic from a caching perspective](https://doi.org/10.1007/s13173-013-0112-z ver seção 6.2 e Figura 8) investiga como a popularidade de objetos impacta o desempenho de sistemas de cache em redes P2P, como o BitTorrent, e propõe o uso de distribuições Weibull e Log-Normal para modelar esse comportamento. Ao replicar esse experimento, focamos na importância de ajustar a política de cache com base na natureza do tráfego de dados, promovendo uma arquitetura de software eficiente para serviços que envolvem alta demanda de armazenamento e processamento, como redes P2P.

## Equações relevantes

Distribuição Weibull: [``weibull.data``](weibull.data);

Distribuição Log-Normal: [``log_normal.data``](log_normal.data);

## Códigos

Gráfico: Código [``grafico.py``](grafico.py);

Dados: Código [``data.java``](data.java);
