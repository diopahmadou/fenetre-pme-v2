import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IContactPme } from 'app/shared/model/contact-pme.model';

type EntityResponseType = HttpResponse<IContactPme>;
type EntityArrayResponseType = HttpResponse<IContactPme[]>;

@Injectable({ providedIn: 'root' })
export class ContactPmeService {
    public resourceUrl = SERVER_API_URL + 'api/contact-pmes';

    constructor(protected http: HttpClient) {}

    create(contactPme: IContactPme): Observable<EntityResponseType> {
        return this.http.post<IContactPme>(this.resourceUrl, contactPme, { observe: 'response' });
    }

    update(contactPme: IContactPme): Observable<EntityResponseType> {
        return this.http.put<IContactPme>(this.resourceUrl, contactPme, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IContactPme>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IContactPme[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
