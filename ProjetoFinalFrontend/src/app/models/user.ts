export interface User {
  id: number;
  username?: string | null;
  email?: string | null;
  photoName?: string | null;
  role?: string | null;
  playerId?: number | null;
  coachId?: number | null;
  credentialId?: number | null;
}
