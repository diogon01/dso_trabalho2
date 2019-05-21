import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IPais } from 'app/shared/model/pais.model';
import { AccountService } from 'app/core';
import { PaisService } from './pais.service';

@Component({
  selector: 'jhi-pais',
  templateUrl: './pais.component.html'
})
export class PaisComponent implements OnInit, OnDestroy {
  pais: IPais[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected paisService: PaisService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.paisService
      .query()
      .pipe(
        filter((res: HttpResponse<IPais[]>) => res.ok),
        map((res: HttpResponse<IPais[]>) => res.body)
      )
      .subscribe(
        (res: IPais[]) => {
          this.pais = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInPais();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IPais) {
    return item.id;
  }

  registerChangeInPais() {
    this.eventSubscriber = this.eventManager.subscribe('paisListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
