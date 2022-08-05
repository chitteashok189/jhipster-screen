import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { PartyRelationshipTypeService } from '../service/party-relationship-type.service';

import { PartyRelationshipTypeComponent } from './party-relationship-type.component';

describe('PartyRelationshipType Management Component', () => {
  let comp: PartyRelationshipTypeComponent;
  let fixture: ComponentFixture<PartyRelationshipTypeComponent>;
  let service: PartyRelationshipTypeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [PartyRelationshipTypeComponent],
    })
      .overrideTemplate(PartyRelationshipTypeComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PartyRelationshipTypeComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(PartyRelationshipTypeService);

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
    expect(comp.partyRelationshipTypes?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
