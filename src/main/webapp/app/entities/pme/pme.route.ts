import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Pme } from 'app/shared/model/pme.model';
import { PmeService } from './pme.service';
import { PmeComponent } from './pme.component';
import { PmeDetailComponent } from './pme-detail.component';
import { PmeUpdateComponent } from './pme-update.component';
import { PmeDeletePopupComponent } from './pme-delete-dialog.component';
import { IPme } from 'app/shared/model/pme.model';

@Injectable({ providedIn: 'root' })
export class PmeResolve implements Resolve<IPme> {
    constructor(private service: PmeService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Pme> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Pme>) => response.ok),
                map((pme: HttpResponse<Pme>) => pme.body)
            );
        }
        return of(new Pme());
    }
}

export const pmeRoute: Routes = [
    {
        path: 'pme',
        component: PmeComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'Pmes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'pme/:id/view',
        component: PmeDetailComponent,
        resolve: {
            pme: PmeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Pmes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'pme/new',
        component: PmeUpdateComponent,
        resolve: {
            pme: PmeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Pmes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'pme/:id/edit',
        component: PmeUpdateComponent,
        resolve: {
            pme: PmeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Pmes'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const pmePopupRoute: Routes = [
    {
        path: 'pme/:id/delete',
        component: PmeDeletePopupComponent,
        resolve: {
            pme: PmeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Pmes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
