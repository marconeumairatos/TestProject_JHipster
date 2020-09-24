import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { IUmfrage } from 'app/shared/model/umfrage.model';

type EntityResponseType = HttpResponse<IUmfrage>;
type EntityArrayResponseType = HttpResponse<IUmfrage[]>;

@Injectable({ providedIn: 'root' })
export class UmfrageService {
  public resourceUrl = SERVER_API_URL + 'api/umfrages';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/umfrages';

  constructor(protected http: HttpClient) {}

  create(umfrage: IUmfrage): Observable<EntityResponseType> {
    return this.http.post<IUmfrage>(this.resourceUrl, umfrage, { observe: 'response' });
  }

  update(umfrage: IUmfrage): Observable<EntityResponseType> {
    return this.http.put<IUmfrage>(this.resourceUrl, umfrage, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IUmfrage>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IUmfrage[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IUmfrage[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
