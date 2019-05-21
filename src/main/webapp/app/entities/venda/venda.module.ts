import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { DsoTrabalho2SharedModule } from 'app/shared';
import {
  VendaComponent,
  VendaDetailComponent,
  VendaUpdateComponent,
  VendaDeletePopupComponent,
  VendaDeleteDialogComponent,
  vendaRoute,
  vendaPopupRoute
} from './';

const ENTITY_STATES = [...vendaRoute, ...vendaPopupRoute];

@NgModule({
  imports: [DsoTrabalho2SharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [VendaComponent, VendaDetailComponent, VendaUpdateComponent, VendaDeleteDialogComponent, VendaDeletePopupComponent],
  entryComponents: [VendaComponent, VendaUpdateComponent, VendaDeleteDialogComponent, VendaDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DsoTrabalho2VendaModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
