import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import SeatService from './seat.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import BookingService from '@/entities/booking/booking.service';
import { type IBooking } from '@/shared/model/booking.model';
import ScreenService from '@/entities/screen/screen.service';
import { type IScreen } from '@/shared/model/screen.model';
import { type ISeat, Seat } from '@/shared/model/seat.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'SeatUpdate',
  setup() {
    const seatService = inject('seatService', () => new SeatService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const seat: Ref<ISeat> = ref(new Seat());

    const bookingService = inject('bookingService', () => new BookingService());

    const bookings: Ref<IBooking[]> = ref([]);

    const screenService = inject('screenService', () => new ScreenService());

    const screens: Ref<IScreen[]> = ref([]);
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'en'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveSeat = async seatId => {
      try {
        const res = await seatService().find(seatId);
        seat.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.seatId) {
      retrieveSeat(route.params.seatId);
    }

    const initRelationships = () => {
      bookingService()
        .retrieve()
        .then(res => {
          bookings.value = res.data;
        });
      screenService()
        .retrieve()
        .then(res => {
          screens.value = res.data;
        });
    };

    initRelationships();

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      seatNumber: {},
      seatType: {},
      booking: {},
      screen: {},
    };
    const v$ = useVuelidate(validationRules, seat as any);
    v$.value.$validate();

    return {
      seatService,
      alertService,
      seat,
      previousState,
      isSaving,
      currentLanguage,
      bookings,
      screens,
      v$,
      t$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.seat.id) {
        this.seatService()
          .update(this.seat)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('cinemaSystemApp.seat.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.seatService()
          .create(this.seat)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('cinemaSystemApp.seat.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
