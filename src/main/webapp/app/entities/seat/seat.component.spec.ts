/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';

import Seat from './seat.vue';
import SeatService from './seat.service';
import AlertService from '@/shared/alert/alert.service';

type SeatComponentType = InstanceType<typeof Seat>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('Seat Management Component', () => {
    let seatServiceStub: SinonStubbedInstance<SeatService>;
    let mountOptions: MountingOptions<SeatComponentType>['global'];

    beforeEach(() => {
      seatServiceStub = sinon.createStubInstance<SeatService>(SeatService);
      seatServiceStub.retrieve.resolves({ headers: {} });

      alertService = new AlertService({
        i18n: { t: vitest.fn() } as any,
        bvToast: {
          toast: vitest.fn(),
        } as any,
      });

      mountOptions = {
        stubs: {
          bModal: bModalStub as any,
          'font-awesome-icon': true,
          'b-badge': true,
          'b-button': true,
          'router-link': true,
        },
        directives: {
          'b-modal': {},
        },
        provide: {
          alertService,
          seatService: () => seatServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        seatServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        const wrapper = shallowMount(Seat, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(seatServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.seats[0]).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
    describe('Handles', () => {
      let comp: SeatComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(Seat, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        seatServiceStub.retrieve.reset();
        seatServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('Should call delete service on confirmDelete', async () => {
        // GIVEN
        seatServiceStub.delete.resolves({});

        // WHEN
        comp.prepareRemove({ id: 123 });

        comp.removeSeat();
        await comp.$nextTick(); // clear components

        // THEN
        expect(seatServiceStub.delete.called).toBeTruthy();

        // THEN
        await comp.$nextTick(); // handle component clear watch
        expect(seatServiceStub.retrieve.callCount).toEqual(1);
      });
    });
  });
});
