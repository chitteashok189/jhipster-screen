import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { PartyRelationshipService } from '../service/party-relationship.service';

import { PartyRelationshipComponent } from './party-relationship.component';

describe('PartyRelationship Management Component', () => {
  let comp: PartyRelationshipComponent;
  let fixture: ComponentFixture<PartyRelationshipComponent>;
  let service: PartyRelationshipService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [PartyRelationshipComponent],
    })
      .overrideTemplate(PartyRelationshipComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PartyRelationshipComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(PartyRelationshipService);

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ id: 123 }],
          headers,
        })
      )
    );
  });

  it('Should call load all on init', () => {
    // WHEN
    comp.ngOnInit();

    // THEN
    expect(service.query).toHaveBeenCalled();
    expect(comp.partyRelationships?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
