import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAnuncioHistorico } from 'app/shared/model/anuncio-historico.model';

@Component({
  selector: 'jhi-anuncio-historico-detail',
  templateUrl: './anuncio-historico-detail.component.html'
})
export class AnuncioHistoricoDetailComponent implements OnInit {
  anuncioHistorico: IAnuncioHistorico;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ anuncioHistorico }) => {
      this.anuncioHistorico = anuncioHistorico;
    });
  }

  previousState() {
    window.history.back();
  }
}
