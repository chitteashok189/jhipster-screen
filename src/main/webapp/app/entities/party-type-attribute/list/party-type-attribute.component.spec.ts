import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { PartyTypeAttributeService } from '../service/party-type-attribute.service';

import { PartyTypeAttributeComponent } from './party-type-attribute.component';

describe('PartyTypeAttribute Management Component', () => {
  let comp: PartyTypeAttributeComponent;
  let fixture: ComponentFixture<PartyTypeAttributeComponent>;
  let service: PartyTypeAttributeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [PartyTypeAttributeComponent],
    })
      .overrideTemplate(PartyTypeAttributeComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PartyTypeAttributeComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(PartyTypeAttributeService);

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
    expect(comp.partyTypeAttributes?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
