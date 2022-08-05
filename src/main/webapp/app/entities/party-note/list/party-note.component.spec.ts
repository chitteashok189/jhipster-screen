import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { PartyNoteService } from '../service/party-note.service';

import { PartyNoteComponent } from './party-note.component';

describe('PartyNote Management Component', () => {
  let comp: PartyNoteComponent;
  let fixture: ComponentFixture<PartyNoteComponent>;
  let service: PartyNoteService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [PartyNoteComponent],
    })
      .overrideTemplate(PartyNoteComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PartyNoteComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(PartyNoteService);

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
    expect(comp.partyNotes?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
