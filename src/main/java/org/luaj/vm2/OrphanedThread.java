package org.luaj.vm2;


public class OrphanedThread extends Error {

   public OrphanedThread() {
      super("orphaned thread");
   }
}
