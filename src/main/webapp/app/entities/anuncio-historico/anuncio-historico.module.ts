import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { DsoTrabalho2SharedModule } from 'app/shared';
import {
  AnuncioHistoricoComponent,
  AnuncioHistoricoDetailComponent,
  AnuncioHistoricoUpdateComponent,
  AnuncioHistoricoDeletePopupComponent,
  AnuncioHistoricoDeleteDialogComponent,
  anuncioHistoricoRoute,
  anuncioHistoricoPopupRoute
} from './';

const ENTITY_STATES = [...anuncioHistoricoRoute, ...anuncioHistoricoPopupRoute];

@NgModule({
  imports: [DsoTrabalho2SharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    AnuncioHistoricoComponent,
    AnuncioHistoricoDetailComponent,
    AnuncioHistoricoUpdateComponent,
    AnuncioHistoricoDeleteDialogComponent,
    AnuncioHistoricoDeletePopupComponent
  ],
  entryComponents: [
    AnuncioHistoricoComponent,
    AnuncioHistoricoUpdateComponent,
    AnuncioHistoricoDeleteDialogComponent,
    AnuncioHistoricoDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DsoTrabalho2AnuncioHistoricoModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
