import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Imf } from 'app/shared/model/imf.model';
import { ImfService } from './imf.service';
import { ImfComponent } from './imf.component';
import { ImfDetailComponent } from './imf-detail.component';
import { ImfUpdateComponent } from './imf-update.component';
import { ImfDeletePopupComponent } from './imf-delete-dialog.component';
import { IImf } from 'app/shared/model/imf.model';

@Injectable({ providedIn: 'root' })
export class ImfResolve implements Resolve<IImf> {
    constructor(private service: ImfService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Imf> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Imf>) => response.ok),
                map((imf: HttpResponse<Imf>) => imf.body)
            );
        }
        return of(new Imf());
    }
}

export const imfRoute: Routes = [
    {
        path: 'imf',
        component: ImfComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'Imfs'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'imf/:id/view',
        component: ImfDetailComponent,
        resolve: {
            imf: ImfResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Imfs'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'imf/new',
        component: ImfUpdateComponent,
        resolve: {
            imf: ImfResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Imfs'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'imf/:id/edit',
        component: ImfUpdateComponent,
        resolve: {
            imf: ImfResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Imfs'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const imfPopupRoute: Routes = [
    {
        path: 'imf/:id/delete',
        component: ImfDeletePopupComponent,
        resolve: {
            imf: ImfResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Imfs'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
