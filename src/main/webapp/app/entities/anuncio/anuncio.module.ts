import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { DsoTrabalho2SharedModule } from 'app/shared';
import {
  AnuncioComponent,
  AnuncioDetailComponent,
  AnuncioUpdateComponent,
  AnuncioDeletePopupComponent,
  AnuncioDeleteDialogComponent,
  anuncioRoute,
  anuncioPopupRoute
} from './';

const ENTITY_STATES = [...anuncioRoute, ...anuncioPopupRoute];

@NgModule({
  imports: [DsoTrabalho2SharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    AnuncioComponent,
    AnuncioDetailComponent,
    AnuncioUpdateComponent,
    AnuncioDeleteDialogComponent,
    AnuncioDeletePopupComponent
  ],
  entryComponents: [AnuncioComponent, AnuncioUpdateComponent, AnuncioDeleteDialogComponent, AnuncioDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DsoTrabalho2AnuncioModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
