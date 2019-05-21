/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DsoTrabalho2TestModule } from '../../../test.module';
import { AnuncioDetailComponent } from 'app/entities/anuncio/anuncio-detail.component';
import { Anuncio } from 'app/shared/model/anuncio.model';

describe('Component Tests', () => {
  describe('Anuncio Management Detail Component', () => {
    let comp: AnuncioDetailComponent;
    let fixture: ComponentFixture<AnuncioDetailComponent>;
    const route = ({ data: of({ anuncio: new Anuncio(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DsoTrabalho2TestModule],
        declarations: [AnuncioDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(AnuncioDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AnuncioDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.anuncio).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
