import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ContactPme } from 'app/shared/model/contact-pme.model';
import { ContactPmeService } from './contact-pme.service';
import { ContactPmeComponent } from './contact-pme.component';
import { ContactPmeDetailComponent } from './contact-pme-detail.component';
import { ContactPmeUpdateComponent } from './contact-pme-update.component';
import { ContactPmeDeletePopupComponent } from './contact-pme-delete-dialog.component';
import { IContactPme } from 'app/shared/model/contact-pme.model';

@Injectable({ providedIn: 'root' })
export class ContactPmeResolve implements Resolve<IContactPme> {
    constructor(private service: ContactPmeService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ContactPme> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<ContactPme>) => response.ok),
                map((contactPme: HttpResponse<ContactPme>) => contactPme.body)
            );
        }
        return of(new ContactPme());
    }
}

export const contactPmeRoute: Routes = [
    {
        path: 'contact-pme',
        component: ContactPmeComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'ContactPmes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'contact-pme/:id/view',
        component: ContactPmeDetailComponent,
        resolve: {
            contactPme: ContactPmeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ContactPmes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'contact-pme/new',
        component: ContactPmeUpdateComponent,
        resolve: {
            contactPme: ContactPmeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ContactPmes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'contact-pme/:id/edit',
        component: ContactPmeUpdateComponent,
        resolve: {
            contactPme: ContactPmeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ContactPmes'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const contactPmePopupRoute: Routes = [
    {
        path: 'contact-pme/:id/delete',
        component: ContactPmeDeletePopupComponent,
        resolve: {
            contactPme: ContactPmeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ContactPmes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
