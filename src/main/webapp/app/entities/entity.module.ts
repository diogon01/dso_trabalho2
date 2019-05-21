import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'estado',
        loadChildren: './estado/estado.module#DsoTrabalho2EstadoModule'
      },
      {
        path: 'pais',
        loadChildren: './pais/pais.module#DsoTrabalho2PaisModule'
      },
      {
        path: 'venda',
        loadChildren: './venda/venda.module#DsoTrabalho2VendaModule'
      },
      {
        path: 'anuncio',
        loadChildren: './anuncio/anuncio.module#DsoTrabalho2AnuncioModule'
      },
      {
        path: 'endereco',
        loadChildren: './endereco/endereco.module#DsoTrabalho2EnderecoModule'
      },
      {
        path: 'anuncio-historico',
        loadChildren: './anuncio-historico/anuncio-historico.module#DsoTrabalho2AnuncioHistoricoModule'
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ],
  declarations: [],
  entryComponents: [],
  providers: [],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DsoTrabalho2EntityModule {}
