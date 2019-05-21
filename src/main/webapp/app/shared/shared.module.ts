import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { DsoTrabalho2SharedLibsModule, DsoTrabalho2SharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective } from './';

@NgModule({
  imports: [DsoTrabalho2SharedLibsModule, DsoTrabalho2SharedCommonModule],
  declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective],
  entryComponents: [JhiLoginModalComponent],
  exports: [DsoTrabalho2SharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DsoTrabalho2SharedModule {
  static forRoot() {
    return {
      ngModule: DsoTrabalho2SharedModule
    };
  }
}
