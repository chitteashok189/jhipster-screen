import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PartyNoteDetailComponent } from './party-note-detail.component';

describe('PartyNote Management Detail Component', () => {
  let comp: PartyNoteDetailComponent;
  let fixture: ComponentFixture<PartyNoteDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PartyNoteDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ partyNote: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(PartyNoteDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(PartyNoteDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load partyNote on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.partyNote).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
