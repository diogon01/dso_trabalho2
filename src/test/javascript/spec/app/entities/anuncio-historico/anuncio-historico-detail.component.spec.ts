/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DsoTrabalho2TestModule } from '../../../test.module';
import { AnuncioHistoricoDetailComponent } from 'app/entities/anuncio-historico/anuncio-historico-detail.component';
import { AnuncioHistorico } from 'app/shared/model/anuncio-historico.model';

describe('Component Tests', () => {
  describe('AnuncioHistorico Management Detail Component', () => {
    let comp: AnuncioHistoricoDetailComponent;
    let fixture: ComponentFixture<AnuncioHistoricoDetailComponent>;
    const route = ({ data: of({ anuncioHistorico: new AnuncioHistorico(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DsoTrabalho2TestModule],
        declarations: [AnuncioHistoricoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(AnuncioHistoricoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AnuncioHistoricoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.anuncioHistorico).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
