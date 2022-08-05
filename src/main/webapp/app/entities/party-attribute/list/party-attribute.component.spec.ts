import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { PartyAttributeService } from '../service/party-attribute.service';

import { PartyAttributeComponent } from './party-attribute.component';

describe('PartyAttribute Management Component', () => {
  let comp: PartyAttributeComponent;
  let fixture: ComponentFixture<PartyAttributeComponent>;
  let service: PartyAttributeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [PartyAttributeComponent],
    })
      .overrideTemplate(PartyAttributeComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PartyAttributeComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(PartyAttributeService);

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
    expect(comp.partyAttributes?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
