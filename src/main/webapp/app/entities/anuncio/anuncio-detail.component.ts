import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAnuncio } from 'app/shared/model/anuncio.model';

@Component({
  selector: 'jhi-anuncio-detail',
  templateUrl: './anuncio-detail.component.html'
})
export class AnuncioDetailComponent implements OnInit {
  anuncio: IAnuncio;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ anuncio }) => {
      this.anuncio = anuncio;
    });
  }

  previousState() {
    window.history.back();
  }
}
