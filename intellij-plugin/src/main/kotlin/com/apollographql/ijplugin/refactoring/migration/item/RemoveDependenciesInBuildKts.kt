package com.apollographql.ijplugin.refactoring.migration.item

import com.intellij.openapi.project.Project
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiMigration
import com.intellij.psi.search.FilenameIndex
import com.intellij.psi.search.GlobalSearchScope
import org.jetbrains.kotlin.psi.KtCallExpression
import org.jetbrains.kotlin.psi.KtFile
import org.jetbrains.kotlin.psi.KtLiteralStringTemplateEntry
import org.jetbrains.kotlin.psi.KtTreeVisitorVoid

class RemoveDependenciesInBuildKts(
    private vararg val groupAndArtifact: String,
) : MigrationItem(), DeletesElements {
  override fun findUsages(project: Project, migration: PsiMigration, searchScope: GlobalSearchScope): List<MigrationItemUsageInfo> {
    val buildGradleKtsFiles: Array<PsiFile> = FilenameIndex.getFilesByName(project, "build.gradle.kts", searchScope)
    val usages = mutableListOf<MigrationItemUsageInfo>()
    for (file in buildGradleKtsFiles) {
      if (file !is KtFile) continue
      file.accept(object : KtTreeVisitorVoid() {
        override fun visitLiteralStringTemplateEntry(entry: KtLiteralStringTemplateEntry) {
          super.visitLiteralStringTemplateEntry(entry)
          if (groupAndArtifact.any { entry.text.contains(it) }) {
            val callExpression = entry.parent.parent.parent.parent as? KtCallExpression
            if (callExpression?.calleeExpression?.text in listOf("implementation", "api", "testImplementation", "testApi")) {
              usages.add(callExpression!!.toMigrationItemUsageInfo())
            }
          }
        }
      })
    }
    return usages
  }

  override fun performRefactoring(project: Project, migration: PsiMigration, usage: MigrationItemUsageInfo) {
    usage.element.delete()
  }
}
